package com.first.family.basic;

import com.first.family.basic.config.CommonConfig;
import com.first.family.basic.distribute.HaCallableTask;
import com.first.family.basic.distribute.HaCallableTask2;
import com.hazelcast.collection.IList;
import com.hazelcast.config.Config;
import com.hazelcast.config.ExecutorConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @description: 分布式计算 Executor Service  https://www.jianshu.com/p/b2f6a0ad831d
 * @author: cuiweiman
 * @date: 2023/7/18 14:27
 */
public class HaExecuteService {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorConfig executorConfig = new ExecutorConfig();
        executorConfig.setName("executor-service")
                .setPoolSize(5)
                .setQueueCapacity(100)
                .setStatisticsEnabled(true)
                .setSplitBrainProtectionName("SplitBrainProtectionName");
        Map<String, ExecutorConfig> executorConfigs = new HashMap<>(4);
        executorConfigs.put("executor-service", executorConfig);

        Config config = CommonConfig.baseConfig("distributed-executor-test");
        config.setExecutorConfigs(executorConfigs);

        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        IExecutorService executor = instance.getExecutorService("executor");

        IList<Integer> list = instance.getList("list");
        list.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Future<Integer> sum = executor.submit(new HaCallableTask());
        System.out.println(sum.get());

        Future<String> localMember = executor.submit(new HaCallableTask2());
        System.out.println(localMember);
        instance.shutdown();
    }
}
