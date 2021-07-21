package com.gzz.retail.infra.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单超时自动取消监听
 * 监听的是死信队列。
 */
// @Component
public class OrderCancelListener implements ChannelAwareMessageListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    @RabbitListener(queues = "q.dlx" , ackMode = "MANUAL")
    public void onMessage(Message message, Channel channel) throws Exception {
        String orderId = new String(message.getBody());
        System.out.println("取消订单：" + orderId);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    public void main(String[] args) {
        //对于订单提交，正常提交后同时投递多一份到延迟队列里面去，用作延迟取消。
//        // 构建订单信息
//        Order order = new Order();
//        order.setUserId(IdUtils.generateUserId());
//        order.setOrderId(IdUtils.generateOrderId());
//        // 设置状态为待支付
//        order.setStatus(OrderStatus.TO_BE_PAYED.toString());
//        order.setDetails(details);
//
//        // 投递消息
//        CorrelationData correlationData = new CorrelationData();
//        rabbitTemplate.convertAndSend("x.order","k.order", order, correlationData);
//        // 同步等待，可以设置为异步回调
//        CorrelationData.Confirm confirm = correlationData.getFuture().get();
//        // 判断发送的消息是否得到broker的确认
//        boolean confirmAck = confirm.isAck();
//        if (confirmAck){
//            // 发送延迟等待消息
//            rabbitTemplate.convertAndSend("x.ttl","k.ttl" , order.getOrderId());
//        }
    }
}