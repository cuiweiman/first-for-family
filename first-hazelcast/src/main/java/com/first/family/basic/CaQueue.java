package com.first.family.basic;

import com.first.family.basic.config.CommonConfig;
import com.hazelcast.collection.IQueue;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.concurrent.TimeUnit;

/**
 * @description: 分布式queue https://www.jianshu.com/p/ea0416297424
 * @author: cuiweiman
 * @date: 2023/7/17 16:37
 */
public class CaQueue {

    public static void main(String[] args) throws Exception {
        queueDemo();
    }

    public static void queueDemo() throws InterruptedException {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(CommonConfig.baseConfig("queue-cluster"));
        IQueue<Integer> queue = instance.getQueue("data");
        queue.put(1);
        Integer item = queue.take();
        System.out.println(item);

        // 阻塞式::插入元素到队尾, 无空间时等待 timeout 时间
        boolean offerResult = queue.offer(2, 1, TimeUnit.SECONDS);
        System.out.println(offerResult);

        // 阻塞式::尝试获取并移除对头元素, 等待 l 时间
        Integer nextItem = queue.poll(1, TimeUnit.SECONDS);
        System.out.println(nextItem);

        instance.shutdown();
    }

}
