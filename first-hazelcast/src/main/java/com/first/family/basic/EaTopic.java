package com.first.family.basic;

import com.first.family.basic.config.CommonConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 发布和订阅都是集群级操作，一个member订阅主题，其实是订阅了集群内所有member发布到主题的消息，
 * 即使member是在订阅之后加入集群。
 *
 * @description: 主题（Topic） https://www.jianshu.com/p/bf2d26532923
 * @author: cuiweiman
 * @date: 2023/7/17 17:42
 */
public class EaTopic {
    public static HazelcastInstance instance;
    public static HazelcastInstance instance2;
    public static CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) throws Exception {
        instance = Hazelcast.newHazelcastInstance(CommonConfig.baseConfig("topic-cluster"));
        instance2 = Hazelcast.newHazelcastInstance(CommonConfig.baseConfig("topic-cluster"));
        new Thread(() -> publish()).start();
        new Thread(() -> subscribe()).start();
        latch.await();
        instance.shutdown();
        instance2.shutdown();
    }

    public static void publish() {
        ITopic<String> topic = instance.getTopic("topic");
        for (int i = 0; i < 10; i++) {
            topic.publish("hello hazelcast topic " + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("发布结束");
        latch.countDown();
    }

    public static void subscribe() {
        ITopic<String> topic = instance2.getTopic("topic");
        topic.addMessageListener(message -> System.out.println("[subscribe]: " + message.getPublishTime() + " " + message.getMessageObject()));
        System.out.println("订阅成功");
        latch.countDown();
    }
}
