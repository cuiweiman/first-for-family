package com.first.family.basic;

import com.first.family.basic.config.CommonConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.ReliableTopicConfig;
import com.hazelcast.config.RingbufferConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.TopicOverloadPolicy;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @description: 可靠主题 https://www.jianshu.com/p/5482cf60b88c
 * @author: cuiweiman
 * @date: 2023/7/18 11:04
 */
public class FaReliableTopic {

    public static HazelcastInstance instance = Hazelcast.newHazelcastInstance(reliableTopicConfig());
    public static HazelcastInstance instance2 = Hazelcast.newHazelcastInstance(reliableTopicConfig());
    public static CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) throws Exception {
        new Thread(() -> reliablePublish()).start();
        new Thread(() -> reliableSubscribe()).start();
        latch.await();
        instance.shutdown();
        instance2.shutdown();
    }

    public static void reliablePublish() {
        ITopic<String> reliableTopic = instance.getReliableTopic("ReliableTopic");
        for (int i = 0; i < 10; i++) {
            reliableTopic.publish("reliable-msg-" + i);
            try {
                TimeUnit.MILLISECONDS.sleep(200L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("发布完成");
        latch.countDown();
    }

    public static void reliableSubscribe() {
        ITopic<String> reliableTopic = instance.getReliableTopic("ReliableTopic");
        reliableTopic.addMessageListener((msg) -> System.out.println("[subscribe] " + msg.getMessageObject()));
        System.out.println("订阅结束");
        latch.countDown();
    }

    public static Config reliableTopicConfig() {
        ReliableTopicConfig reliableTopicConfig = new ReliableTopicConfig();
        reliableTopicConfig.setTopicOverloadPolicy(TopicOverloadPolicy.BLOCK);
        HashMap<String, ReliableTopicConfig> reliableTopicConfigMap = new HashMap<>(4);
        reliableTopicConfigMap.put("reliable-topic", reliableTopicConfig);

        RingbufferConfig ringbufferConfig = new RingbufferConfig();
        ringbufferConfig.setCapacity(1000);
        ringbufferConfig.setTimeToLiveSeconds(5);
        HashMap<String, RingbufferConfig> ringBufferConfigMap = new HashMap<>(4);
        ringBufferConfigMap.put("reliable-ringBuffer", ringbufferConfig);

        Config config = CommonConfig.baseConfig();
        config.setReliableTopicConfigs(reliableTopicConfigMap);
        config.setRingbufferConfigs(ringBufferConfigMap);
        return config;
    }

}
