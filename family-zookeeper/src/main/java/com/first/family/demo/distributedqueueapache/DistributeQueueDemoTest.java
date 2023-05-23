package com.first.family.demo.distributedqueueapache;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.CloseableUtils;
import org.junit.jupiter.api.Test;

/**
 * Zookeeper分布式队列使用Demo
 *
 * @description: 分布式队列
 * @author: cuiweiman
 * @date: 2022/12/26 19:08
 */
public class DistributeQueueDemoTest {

    @Test
    public void testOffer1() throws Exception {
        //序列化数据
        QueueSerializer<String> queueSerializer = new MyQueueSerializer();
        //消费者
        QueueConsumer<String> consumer = new MyQueueConsumer();

        String hostPort = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        // 重连策略: 重连3次，每隔 100ms 重连一次
        RetryPolicy retryPolicy = new RetryNTimes(3, 100);
        int sessionTimeoutMs = 30 * 60 * 1000;
        int connectionTimeoutMs = 5 * 1000;

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(hostPort)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs)
                .retryPolicy(retryPolicy).build();

        client.start();
        /*
         * client：客户端配置信息
         * consumer：消费者，它可以接收队列的数据。
         * queueSerializer：对队列中的对象的序列化和反序列化
         * queuePath：PERSISTENT-SEQUENTIAL：持久化有序节点
         * lockPath：TRANSIENT-SEQUENTIAL：消费者加锁， 当消费者消费数据时持有锁，这样其它消费者不能消费此消息，性能损失。
         */
        DistributedQueue<String> queue = QueueBuilder
                .builder(client, consumer, queueSerializer, "/queue")
                .lockPath("/queueLock-").buildQueue();
        queue.start();

        for (int i = 0; i < 10; i++) {
            // 生产数据
            queue.put("test-" + Thread.currentThread().getName());
        }
        // 休眠等待消费
        Thread.sleep(60000);
        CloseableUtils.closeQuietly(queue);
        CloseableUtils.closeQuietly(client);

    }


}
