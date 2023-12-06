package com.example.server.utils.listener;

import com.example.server.pojo.Orderitem;
import com.example.server.service.IOrderitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderListener {

    @Autowired
    private IOrderitemService orderService;

    @Autowired
    private KafkaTemplate<String, Orderitem> kafkaTemplate;
    @Autowired
    WebSocketServer ws;

    @KafkaListener(topics = "order-request-topic", groupId = "order-processing-group")
    public void listenOrderRequest(Orderitem orderitem) {
        try {
            // 监听到下订单消息后，调用订单服务处理订单
            System.out.println("监听到订单");
            orderService.save(orderitem);
            //处理订单完成后将订单发送到结果Topic中
            kafkaTemplate.send("order-result-topic", orderitem);
            // 可以在这里添加日志或其他操作

        } catch (Exception e) {
            // 处理失败的情况
            // 可以在这里添加错误处理逻辑
        }
    }

    @KafkaListener(topics = "order-result-topic", groupId = "order-result-group")
    public void listenOrderResult(Orderitem orderitem) {
        // 监听订单处理结果，每次有订单完成会进入这里
        // 在控制台输出订单处理结果
        System.out.println("result Listener: Order result: " + orderitem);
        String key=orderitem.username;
        ws.sendMessageToUser(key,"订单已完成");
    }
}
