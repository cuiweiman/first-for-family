package concurrency.disruptor;

import concurrency.disruptor.common.Order;
import concurrency.disruptor.common.OrderHandler;
import concurrency.disruptor.common.OrderProducer;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 主要演示多生产者能力，消费者可以根据实际情况配置，或者参考 SingleMultiDemo
 * <p>
 * ProducerType.MULTI
 *
 * @description: 多生产者 单消费者
 * @author: cuiweiman
 * @date: 2023/12/26 16:12
 */
public class MultiSingleDemo {
    public static void main(String[] args) {
        Disruptor<Order> disruptor = new Disruptor<>(Order::new, 1024,
                new ThreadFactoryBuilder().setNameFormat("Single-Multi-Chain-%s")
                        .setDaemon(false).setPriority(Thread.NORM_PRIORITY)
                        .setUncaughtExceptionHandler((t, e) -> System.out.printf("threadName %s %n%s ", t.getName(), e.toString()))
                        .build(),
                ProducerType.MULTI,
                new SleepingWaitStrategy()
        );
        disruptor.handleEventsWith(new OrderHandler("c-1"));
        disruptor.start();

        RingBuffer<Order> ringBuffer = disruptor.getRingBuffer();

        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            // 三个生产者线程，开始生产
            new Thread(() -> {
                OrderProducer orderProducer = new OrderProducer(ringBuffer);
                for (int j = 0; j < 5; j++) {
                    orderProducer.onData(Thread.currentThread().getName() + " " + j);
                }
                countDownLatch.countDown();
            }, "生产者-" + i).start();
        }

        // 关闭队列
        try {
            countDownLatch.await(20, TimeUnit.SECONDS);
            disruptor.shutdown(20, TimeUnit.SECONDS);
            System.out.println(" >>> 执行结束");
        } catch (TimeoutException e) {
            System.out.println(" >>> Disruptor 等待超时，还有未处理的事件，不等了，抛出异常");
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println(" >>> CountDownLatch 等待超时，还有未处理的事件，不等了，抛出异常");
            throw new RuntimeException(e);
        }
    }
}

