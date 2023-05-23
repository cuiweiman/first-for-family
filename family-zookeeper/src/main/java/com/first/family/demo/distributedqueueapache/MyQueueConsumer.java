package com.first.family.demo.distributedqueueapache;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.state.ConnectionState;

/**
 * @description: 队列消费者
 * @author: cuiweiman
 * @date: 2022/12/27 14:23
 */
public class MyQueueConsumer implements QueueConsumer<String> {

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        System.out.println("state changed");
    }

    @Override
    public void consumeMessage(String s) {
        //do someThing
        System.out.println(Thread.currentThread().getName() + " 消费数据：" + s);
    }
}
