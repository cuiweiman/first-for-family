package com.first.family.basic;

import com.first.family.basic.config.CommonConfig;
import com.hazelcast.cluster.Cluster;
import com.hazelcast.collection.IQueue;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.DistributedObjectEvent;
import com.hazelcast.core.DistributedObjectListener;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

/**
 * @description: 分布式数据结构简介 https://www.jianshu.com/p/8801ab484a4f
 * @author: cuiweiman
 * @date: 2023/7/17 15:03
 */
public class AaCreateOrDestroy {
    public static void main(String[] args) {
        // 分布式对象的加载和销毁
        // objectCreateOrDestroy();

        // 控制分区
        // selectPartition();

        // 监听分布式对象实例的创建和销毁
        highAvailability();
    }

    /**
     * 分布式对象的加载和销毁
     */
    public static void objectCreateOrDestroy() {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(CommonConfig.baseConfig("CreateOrDestroy"));
        IMap<String, String> data = instance.getMap("data");
        data.put("hazelcast", "a good tool");
        System.out.println("The size of map = " + data.size());
        // 销毁 对象, 缓存数据将全部清除
        data.destroy();
        System.out.println("The size of map = " + data.size());
        Cluster cluster = instance.getCluster();
        System.out.println(cluster);
        instance.shutdown();
    }

    /**
     * 控制分区
     */
    public static void selectPartition() {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(CommonConfig.baseConfig("SelectPartition"));
        IQueue<String> queue1 = instance.getQueue("q1");
        IQueue<String> queue2 = instance.getQueue("q2");
        System.out.println("queue1 partition key = " + queue1.getPartitionKey());
        System.out.println("queue2 partition key = " + queue2.getPartitionKey());

        IQueue<String> queue3 = instance.getQueue("q3@test");
        IQueue<String> queue4 = instance.getQueue("q4@test");
        System.out.println("queue3 partition key = " + queue3.getPartitionKey());
        System.out.println("queue4 partition key = " + queue4.getPartitionKey());

        instance.shutdown();
    }

    /**
     * 监听分布式对象实例的创建和销毁
     */
    public static void highAvailability() {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(CommonConfig.baseConfig("highAvailability"));
        instance.addDistributedObjectListener(new DistributedObjectListener() {
            @Override
            public void distributedObjectCreated(DistributedObjectEvent event) {
                DistributedObject instance = event.getDistributedObject();
                System.out.println(instance.getName() + " created");
            }

            @Override
            public void distributedObjectDestroyed(DistributedObjectEvent event) {
                System.out.println(event.getObjectName() + " destroyed");
            }
        });
        IQueue<String> queue1 = instance.getQueue("q1");
        IQueue<String> queue2 = instance.getQueue("q2");
        queue1.add("hello");
        queue2.add("world");
        instance.getDistributedObjects().forEach(o -> System.out.println(o.getName()));
        instance.getDistributedObjects().forEach(DistributedObject::destroy);

        instance.shutdown();
    }
}
