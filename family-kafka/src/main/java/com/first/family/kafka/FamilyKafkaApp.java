package com.first.family.kafka;

import com.first.family.kafka.demo.ConsumerDemo2;
import com.first.family.kafka.demo.ProducerDemo;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/5/17 15:14
 */
public class FamilyKafkaApp {
    public static void main(String[] args) throws InterruptedException {
        String topic = "test-topic";
        /*ProducerDemo producerDemo = new ProducerDemo();
        for (int i = 0; i < 500; i++) {
            producerDemo.msgSend(topic, "my-test-key", "value" + i);
        }
        TimeUnit.SECONDS.sleep(1);*/
        System.out.println("************************************************");
        List<String> topicList = List.of(topic);
        // ConsumerDemo consumerDemo = new ConsumerDemo(topicList);
        // consumerDemo.consumer();

        ConsumerDemo2 consumerDemo = new ConsumerDemo2("单线程单处理", topicList);
        ExecutorService executors = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.CallerRunsPolicy());
        executors.submit(consumerDemo);
    }

}
