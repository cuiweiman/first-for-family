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

import java.time.LocalDateTime;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 多个 消费者 链式消费
 * <p>
 * c-1 消费 ——> c-2、c-3 再消费 ——> c-4 消费
 * <p>
 * 消费者 C2、C3 只有在 C1 消费完消息m后，才能消费m。
 * 消费者 C4 只有在 C2、C3 消费完m后，才能消费该消息。
 *
 * @description: 单生产者，多消费者，链式消费
 * @author: cuiweiman
 * @date: 2023/12/25 17:34
 */
public class SingleMultiChainDemo {

    public static void main(String[] args) {
        EventFactory<Order> orderFactory = new OrderFactory();
        int ringBufferSize = 1024 * 1024;
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Single-Multi-Chain-%s")
                .setDaemon(false).setPriority(Thread.NORM_PRIORITY)
                .setUncaughtExceptionHandler((t, e) -> System.out.printf("threadName %s %n%s ", t.getName(), e.toString()))
                .build();

        Disruptor<Order> disruptor =
                new Disruptor<>(orderFactory, ringBufferSize, threadFactory, ProducerType.SINGLE, new YieldingWaitStrategy());

        // c1 先消费，c2和c3 再消费，最后 c4 消费，配置消费链，并开启消费线程。按照 消费者添加的顺序 依次进行 链传递
        disruptor.handleEventsWith(new OrderHandler("c-1"))
                .then(new OrderHandler("c-2"), new OrderHandler("c-3"))
                .then(new OrderHandler("c-4"));
        disruptor.start();

        RingBuffer<Order> ringBuffer = disruptor.getRingBuffer();
        OrderProducer orderProducer = new OrderProducer(ringBuffer);
        for (int i = 0; i < 100; i++) {
            orderProducer.onData(LocalDateTime.now() + "\t" + i);
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
