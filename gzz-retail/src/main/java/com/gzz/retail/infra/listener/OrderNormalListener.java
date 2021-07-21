package com.gzz.retail.infra.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 订单正常支付流程监听
 * 用于监听订单正常的支付提交和超时取消。
 *
 * https://www.jianshu.com/p/89fcb4711850
 */
// @Component
public class OrderNormalListener {

    @RabbitListener(queues = "q.order",ackMode = "MANUAL")
    public void onMessage(Object order , Channel channel , Message message) throws IOException {
        System.out.println("写入数据库");
        System.out.println(order);

//        for (OrderDetail detail : order.getDetails()){
//            System.out.println(detail);
//        }
//
        channel.basicAck(message.getMessageProperties().getDeliveryTag() , false);
    }

}
