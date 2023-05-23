package com.first.family.demo.distributedqueueapache;

import org.apache.curator.framework.recipes.queue.QueueSerializer;

import java.nio.charset.StandardCharsets;

/**
 * @description: 队列序列化方法
 * @author: cuiweiman
 * @date: 2022/12/27 14:25
 */
public class MyQueueSerializer implements QueueSerializer<String> {
    @Override
    public byte[] serialize(String item) {
        return item.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String deserialize(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
