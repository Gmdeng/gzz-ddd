package com.gzz.retail.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ配置类
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 使用DirectMessageListenerContainer，您需要确保ConnectionFactory配置了一个任务执行器，
     * 该执行器在使用该ConnectionFactory的所有侦听器容器中具有足够的线程来支持所需的并发性。
     * 默认连接池大小仅为5。
     *
     * 并发性基于配置的队列和consumersPerQueue。每个队列的每个使用者使用一个单独的通道，
     * 并发性由rabbit客户端库控制;默认情况下，它使用5个线程池;
     * 可以配置taskExecutor来提供所需的最大并发性。
     *
     * @param connectionFactory
     * @return
     */
    @Bean(name = "rabbitMessageListenerContainer")
    public DirectMessageListenerContainer listenerContainer(ConnectionFactory connectionFactory){
        // 写的时候，默认使用DEFAULT_NUM_THREADS = Runtime.getRuntime().availableProcessors() * 2个线程
        DirectMessageListenerContainer container = new DirectMessageListenerContainer(connectionFactory);
        // 设置确认消息的模式
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setPrefetchCount(5);
        container.setConsumersPerQueue(5);
        container.setMessagesPerAck(1);

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(20);
        //设置该属性，灵活设置并发 ,多线程运行。
        container.setTaskExecutor(taskExecutor);

        return container;
    }

    /**
     * 设置消息转换器，用于将对象转换成JSON数据
     * 可以通过converterAndSend将对象发送消息队列
     * 监听器也可以通过该工具将接受对象反序列化成java对象
     *
     * @return Jackson转换器
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * 订单消息队列
     * @return
     */
    @Bean
    public Queue orderQueue(){
        return QueueBuilder.durable("q.order").build();
    }

    /**
     * 延迟消息队列
     * @return
     */
    @Bean
    public Queue ttlQueue(){
        Map<String,Object> args = new HashMap<>();
        // 该队列的消息10s到期
        args.put("x-message-ttl", 10000);
        // 设置死信队列交换器,（当队列消息TTL到期后依然没有消费，则加入死信队列）
        args.put("x-dead-letter-exchange","x.dlx");
        // 设置私信队列路由键,设置该队列所关联的死信交换器的routingKey，如果没有特殊指定，使用原队列的routingKey
        args.put("x-dead-letter-routing-key","k.dlx");
        Queue queue = new Queue("q.ttl",true,false,false, args);
        return queue;
    }

    /**
     * 死信队列，用于取消用户订单
     * 当10s还没有付款的订单则进入死信队列，消费死信队列，取消用户订单
     *
     * @return
     */
    @Bean
    public Queue dlxQueue(){
        Map<String,Object> args = new HashMap<>();
        Queue dlq = new Queue("q.dlx",true,false,false, args);

        return dlq;
    }

    /**
     * 订单交换器
     * @return
     */
    @Bean
    public Exchange orderExchange(){
        Map<String, Object> args = new HashMap<>();
        DirectExchange exchange = new DirectExchange("x.order", true, false, args);

        return exchange;
    }

    /**
     * 延迟队列交换器
     * 过期消息的交换机
     * @return
     */
    @Bean
    public Exchange ttlExchange(){
        Map<String, Object> args = new HashMap<>();
        return new DirectExchange("x.ttl", true, false, args);
        //return new DirectExchange("ttl.exchange", true, false);
    }

    /**
     * 死信队列交换器
     * @return
     */
    @Bean
    public Exchange dlxExchange(){
        Map<String, Object> args = new HashMap<>();
        DirectExchange exchange = new DirectExchange("x.dlx", true, false, args);
        return exchange;
    }

    /**
     * 用于发送下单，做分布式事务的MQ
     * @return
     */
    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(orderQueue())
                .to(orderExchange())
                .with("k.order")
                .noargs();
    }

    /**
     * 用于等待用户支付的延迟队列绑定
     * @return
     */
    @Bean
    public Binding ttlBinding(){
        return BindingBuilder.bind(ttlQueue())
                .to(ttlExchange())
                .with("k.ttl")
                .noargs();
    }

    /**
     * 用于支付超时取消用户订单的死信队列绑定
     * @return
     */
    @Bean
    public Binding dlxBinding(){
        return BindingBuilder.bind(dlxQueue())
                .to(dlxExchange())
                .with("k.dlx")
                .noargs();
    }
}
