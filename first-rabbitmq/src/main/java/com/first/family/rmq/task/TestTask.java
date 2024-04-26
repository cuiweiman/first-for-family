package com.first.family.rmq.task;

import com.first.family.rmq.producer.RabbitProducer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2024/4/26 15:17
 */

@Component
@EnableScheduling
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TestTask {

    private final RabbitProducer producer;

    @SneakyThrows
    @Scheduled(cron = "15 * * * * ? ")
    public void task() {
        int times = 5;
        CountDownLatch latch = new CountDownLatch(times);
        for (int i = 0; i < times; i++) {
            new Thread(() -> {
                producer.publish();
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println(new Date() + " 执行结束");
    }
}
