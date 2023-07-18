package com.first.family.basic.listener;

import com.hazelcast.core.DistributedObjectEvent;
import com.hazelcast.core.DistributedObjectListener;

/**
 * @description: 监听分布式对象事件
 * @author: cuiweiman
 * @date: 2023/7/18 13:50
 */
public class GbDistributedObjectListener implements DistributedObjectListener {
    @Override
    public void distributedObjectCreated(DistributedObjectEvent event) {
        System.out.println(event.toString());
    }

    @Override
    public void distributedObjectDestroyed(DistributedObjectEvent event) {
        distributedObjectCreated(event);
    }
}
