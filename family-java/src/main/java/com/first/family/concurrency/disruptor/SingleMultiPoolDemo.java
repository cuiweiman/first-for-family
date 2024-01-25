package com.first.family.concurrency.disruptor;

import com.first.family.concurrency.disruptor.common.Order;
import com.first.family.concurrency.disruptor.common.OrderFactory;
import com.first.family.concurrency.disruptor.common.OrderHandler;
import com.first.family.concurrency.disruptor.common.OrderProducer;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 消费者 线程池消费，并发消费，消费不同的队列元素
 * <p>
 * c1 和 c2 消费者同时 消费队列元素，多线程竞争消费
 * <p>
 * c1和c2竞争消费，c3和c4竞争消费，c5和c6最后广播消费
 *
 * @description: 单生产者-多消费者并发消费
 * @author: cuiweiman
 * @date: 2023/12/26 10:11
 */
public class SingleMultiPoolDemo {
    public static void main(String[] args) {
        OrderFactory orderFactory = new OrderFactory();
        int ringBufferSize = 1024 * 1024;
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("Single-Multi-Pool-%s").setDaemon(false).setPriority(Thread.NORM_PRIORITY)
                .setUncaughtExceptionHandler((t, e) -> System.out.printf("threadName: %s %n%s", t.getName(), e.toString()))
                .build();

        Disruptor<Order> disruptor = new Disruptor<>(orderFactory, ringBufferSize, threadFactory,
                ProducerType.SINGLE, new YieldingWaitStrategy());

        // 使用 WorkerPool 实现竞争消费
        // disruptor.handleEventsWithWorkerPool(new OrderHandler("c-1"), new OrderHandler("c-2"));

        disruptor.handleEventsWithWorkerPool(new OrderHandler("1"), new OrderHandler("2"))
                .thenHandleEventsWithWorkerPool(new OrderHandler("3"), new OrderHandler("4"))
                .then(new OrderHandler("5"), new OrderHandler("6"));

        disruptor.start();

        RingBuffer<Order> ringBuffer = disruptor.getRingBuffer();
        OrderProducer orderProducer = new OrderProducer(ringBuffer);
        for (int i = 0; i < 5; i++) {
            orderProducer.onData(LocalDateTime.now() + " " + i);
        }
        // 关闭队列
        try {
            disruptor.shutdown(20, TimeUnit.SECONDS);
            System.out.println(" >>> 执行结束");
        } catch (TimeoutException e) {
            System.out.println(" >>> 等待超时，还有未处理的事件，不等了，抛出异常");
            throw new RuntimeException(e);
        }

    }
}
