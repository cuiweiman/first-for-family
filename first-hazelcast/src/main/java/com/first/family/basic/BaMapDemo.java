package com.first.family.basic;

import com.first.family.basic.config.BaMapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.concurrent.TimeUnit;

import static com.first.family.basic.utils.SleepUtil.sleep;

/**
 * @description: 分布式map https://www.jianshu.com/p/a015ffb2dd8f
 * @author: cuiweiman
 * @date: 2023/7/17 15:57
 */
public class BaMapDemo {
    public static void main(String[] args) {
        // 缓存的驱逐方式，也可以在配置中配置通用的驱逐策略
        baseDemo();
    }

    public static void baseDemo() {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(BaMapConfig.mapConfig());
        IMap<Integer, String> fruits = instance.getMap("map-sync");
        fruits.put(1, "apple", 10, TimeUnit.SECONDS);
        fruits.put(2, "banana", 10, TimeUnit.SECONDS, 3, TimeUnit.SECONDS);

        System.out.println("====== size " + fruits.size());
        fruits.forEach((k, v) -> System.out.println(k + " " + v));

        sleep(5);
        System.out.println("====== size " + fruits.size());

        sleep(6);
        System.out.println("====== size " + fruits.size());

        instance.shutdown();
    }



}
