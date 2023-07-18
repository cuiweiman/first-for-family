package com.first.family.basic;

import com.first.family.basic.config.CommonConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.MultiMapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.multimap.MultiMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 分布式 且 线程安全 的 MultiMap https://www.jianshu.com/p/cba434a5b849
 * @author: cuiweiman
 * @date: 2023/7/17 16:44
 */
public class DaMultiMap {

    public static void main(String[] args) {
        MultiMapConfig mmConfig = new MultiMapConfig();
        // 同步备份数 和 异步备份数
        mmConfig.setBackupCount(0).setAsyncBackupCount(1)
                // 存储值的集合类型
                .setValueCollectionType(MultiMapConfig.ValueCollectionType.SET)
                // 脑裂保护
                .setSplitBrainProtectionName("SplitBrainProtectionName");

        Map<String, MultiMapConfig> multiMapConfigs = new HashMap<>(4);
        multiMapConfigs.put("multipart-cluster", mmConfig);

        Config config = CommonConfig.baseConfig("multipart-cluster");
        config.setMultiMapConfigs(multiMapConfigs);
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);

        MultiMap<String, Integer> multiMap = instance.getMultiMap("data");
        multiMap.put("even", 2);
        multiMap.put("even", 4);
        multiMap.put("odd", 5);

        multiMap.keySet().forEach(s -> System.out.printf("key = %s,value = %s%n", s, multiMap.get(s)));

        instance.shutdown();
    }
}
