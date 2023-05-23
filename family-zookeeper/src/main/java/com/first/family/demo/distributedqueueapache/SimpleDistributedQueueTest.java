package com.first.family.demo.distributedqueueapache;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.queue.SimpleDistributedQueue;
import org.apache.curator.retry.RetryNTimes;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * {@link SimpleDistributedQueue} 分布式队列的简单使用
 *
 * @description:
 * @author: cuiweiman
 * @date: 2022/12/27 14:35
 */
public class SimpleDistributedQueueTest {


    @Test
    public void testSimpleDistributedQueue() throws Exception {
        String hostPort = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        // 重连策略: 重连3次，每隔 100ms 重连一次
        RetryPolicy retryPolicy = new RetryNTimes(3, 100);
        int sessionTimeoutMs = 30 * 60 * 1000;
        int connectionTimeoutMs = 5 * 1000;
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                hostPort, sessionTimeoutMs, connectionTimeoutMs, retryPolicy);
        client.start();
        String path = "/simple-queue";

        SimpleDistributedQueue queue = new SimpleDistributedQueue(client, path);
        queue.offer("Hello World".getBytes(UTF_8));

        try {
            byte[] element = queue.element();
            System.out.println("{#element()} " + new String(element, UTF_8));
        } catch (NoSuchElementException e) {
            System.out.println("队列为空: " + e.getMessage());
        }

        byte[] peek = queue.peek();
        if (Objects.isNull(peek)) {
            System.out.println("{#peek()} 队列为空");
        } else {
            System.out.println("{#peek()} " + new String(peek, UTF_8));
        }

        byte[] poll = queue.poll(1, TimeUnit.SECONDS);
        if (Objects.isNull(poll)) {
            System.out.println("{#poll()}  由于队列为空，因此阻塞了1s，但之后队列依然为空");
        } else {
            System.out.println("{#poll()} " + new String(poll, UTF_8));
        }

        try {
            byte[] remove = queue.remove();
            System.out.println("{#remove()} " + new String(remove, UTF_8));
        } catch (NoSuchElementException e) {
            System.out.println("{#remove()}  队列为空: " + e.getMessage());
        }

        // 队列为空时，无限阻塞，直到成功获取到队列中的元素
        offerQueue(queue);
        byte[] take = queue.take();
        System.out.println("{#take()} " + new String(take, UTF_8));

    }

    public void offerQueue(SimpleDistributedQueue queue) {
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                queue.offer(Thread.currentThread().getName().concat(" ## Hello SimpleDistributedQueue").getBytes(UTF_8));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
