package com.first.family.basic;

import com.first.family.basic.config.CommonConfig;
import com.first.family.basic.listener.GbDistributedObjectListener;
import com.first.family.basic.utils.SleepUtil;
import com.hazelcast.collection.IQueue;
import com.hazelcast.config.Config;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.topic.ITopic;

/**
 * @description: 监听分布式对象事件  https://www.jianshu.com/p/6fba643313b5
 * @author: cuiweiman
 * @date: 2023/7/18 13:49
 */
public class GbDistributedListener {

    public static void main(String[] args) {
        ListenerConfig listenerConfig = new ListenerConfig(new GbDistributedObjectListener());
        Config config = CommonConfig.baseConfig("DistributedObjectListener");
        config.addListenerConfig(listenerConfig);

        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        IMap<Object, Object> map = instance.getMap("test-map");
        IQueue<Object> queue = instance.getQueue("test-queue");
        ITopic<Object> topic = instance.getTopic("test-topic");
        SleepUtil.sleep(2);
        map.destroy();
        queue.destroy();
        topic.destroy();

        instance.shutdown();
    }

}
