package com.first.family.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @description: SpringBootApplication
 * @author: cuiweiman
 * @date: 2023/8/17 14:35
 */
@EnableKafka
@SpringBootApplication
public class SpringKafkaApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApp.class, args);
    }
}
