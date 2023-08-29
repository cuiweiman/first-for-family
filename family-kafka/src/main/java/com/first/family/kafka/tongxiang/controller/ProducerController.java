package com.first.family.kafka.tongxiang.controller;

import com.first.family.kafka.tongxiang.producer.MyKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/8/17 16:25
 */
@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProducerController {

    public final MyKafkaProducer myKafkaProducer;

    @GetMapping("/send")
    public ResponseEntity<String> sendMsg(String msg) {
        // myKafkaProducer.send("iot_data_topic", msg);
        myKafkaProducer.send("test", msg);
        return ResponseEntity.ok("success");
    }
}
