package com.first.family.concurrency.disruptor.common;

import com.lmax.disruptor.RingBuffer;

/**
 * 队列元素 生产者
 *
 * @description: 生产者
 * @author: cuiweiman
 * @date: 2023/12/25 16:13
 */
public class OrderProducer {

    private final RingBuffer<Order> ringBuffer;

    public OrderProducer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(String data) {
        long sequence = ringBuffer.next();
        try {
            Order order = ringBuffer.get(sequence);
            order.setId(data);
        } finally {
            // RingBuffer 下标 数据 发布
            ringBuffer.publish(sequence);
        }
    }

}
