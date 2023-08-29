package com.first.family.kafka.tongxiang.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @description: 生产者
 * @author: cuiweiman
 * @date: 2023/8/17 14:23
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MyKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * kafka消息发送
     */
    public void send(String topic, String msg) {
        try {
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, msg);
            SendResult<String, String> sendResult = future.get();
            System.out.println(sendResult.toString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }


    }
}
