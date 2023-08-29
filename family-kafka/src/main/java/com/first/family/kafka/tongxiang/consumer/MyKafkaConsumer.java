package com.first.family.kafka.tongxiang.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @description: 消费者
 * @author: cuiweiman
 * @date: 2023/8/17 14:49
 */
@Component
public class MyKafkaConsumer {

    @KafkaListener(id = "listenerForIot", topics = "test", containerFactory = "kafkaFactory")
    public void listenerForIot(String payload) {
        System.out.println(payload);
    }

}
