package concurrency.disruptor;

import concurrency.disruptor.common.Order;
import concurrency.disruptor.common.OrderFactory;
import concurrency.disruptor.common.OrderHandler;
import concurrency.disruptor.common.OrderProducer;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @description: 单生产者-单消费者
 * @author: cuiweiman
 * @date: 2023/12/25 16:26
 */
public class SingleSingleDemo {
    public static void main(String[] args) {
        EventFactory<Order> orderFactory = new OrderFactory();
        int ringBufferSize = 1024 * 1024;
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("单生产者-单消费者-%s").setDaemon(false)
                .setPriority(Thread.NORM_PRIORITY).setUncaughtExceptionHandler((thread, e) -> {
                    // 处理 线程未捕获的 异常
                    System.err.println("Uncaught exception in thread " + thread.getName() + ": " + e.getMessage());
                }).build();

        Disruptor<Order> disruptor =
                new Disruptor<>(orderFactory, ringBufferSize, threadFactory, ProducerType.SINGLE, new YieldingWaitStrategy());

        // 设置 单个 消费者
        disruptor.handleEventsWith(new OrderHandler("Consumer-1"));
        disruptor.start();

        // 创建 单个 生产者
        RingBuffer<Order> ringBuffer = disruptor.getRingBuffer();
        OrderProducer orderProducer = new OrderProducer(ringBuffer);
        for (int i = 0; i < 100; i++) {
            orderProducer.onData(System.currentTimeMillis() + " —— " + i);
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
