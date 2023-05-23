package com.first.family.demo.distrubutedlock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 分布式可重入互斥锁 Distributed Reentrant Mutex
 * 占有锁的线程可以直接再次获得锁(锁计数+1，释放锁时锁计数-1)，而不需要重新排队。
 * 1. 根据当前线程去查看是否存在锁节点，若此时本线程已经获得锁，则锁状态次数+1，并直接返回true；
 * 2. 没有获取到锁，则先创建锁数据，依次监听前节点等待获取锁的通知；
 *
 * @description: 分布式可重入排他锁
 * @author: cuiweiman
 * @date: 2022/12/28 11:47
 */
public class InterProcessMutexService {

    public InterProcessMutex interProcessMutex;

    public InterProcessMutexService(InterProcessMutex interProcessMutex) {
        this.interProcessMutex = interProcessMutex;
    }

    public void createOrderNum() {
        try {
            if (interProcessMutex.acquire(2, TimeUnit.SECONDS)) {
                String orderNum = String.valueOf(System.currentTimeMillis());
                System.out.println(Thread.currentThread().getName() + " 创建了订单号 [ " + orderNum + " ]");
            } else {
                System.out.println(Thread.currentThread().getName() + " 获取锁失败");
            }
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " 获取锁失败: " + e.getMessage());
        } finally {
            try {
                if (interProcessMutex.isOwnedByCurrentThread()) {
                    interProcessMutex.release();
                    System.out.println(Thread.currentThread().getName() + " 释放锁成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + " 释放锁失败: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String hostPort = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        int sessionTimeoutMs = 30 * 60 * 1000;
        int connectionTimeoutMs = 5 * 1000;
        // 重试策略，失败后重连3次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(hostPort)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs)
                .retryPolicy(retryPolicy)
                // 指定隔离名称，表示所有节点的操作都在该工作空间下进行。不指定时使用自定义的Path
                .namespace("Curator_Workspace").build();
        client.start();
        InterProcessMutex interProcessMutex = new InterProcessMutex(client, "/lock/mutex");
        int threadNum = 50;
        // 阻塞线程执行，构造多线程并发执行。当 CountDownLatch 计数器为0时，阻塞方法才会停止并继续执行
        CountDownLatch cdl = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                InterProcessMutexService demo = new InterProcessMutexService(interProcessMutex);
                try {
                    cdl.await();
                    demo.createOrderNum();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            cdl.countDown();
        }
    }


}
