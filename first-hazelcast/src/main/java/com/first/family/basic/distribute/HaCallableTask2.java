package com.first.family.basic.distribute;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/7/18 13:56
 */
public class HaCallableTask2 implements Callable<String>, Serializable, HazelcastInstanceAware {

    private HazelcastInstance hazelcastInstance;

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public String call() {
        System.out.println(Thread.currentThread().getName());
        return hazelcastInstance.getCluster().getLocalMember().toString();
    }
}
