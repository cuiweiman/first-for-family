package com.first.family.concurrency.basic;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 两个线程，一个打印奇数，一个打印偶数
 * @author: cuiweiman
 * @date: 2023/11/8 08:35
 */
public class PrintfThread {

    private static final ExecutorService JI_EXECUTOR = new ThreadPoolExecutor(2, 2,
            2, TimeUnit.SECONDS, new LinkedBlockingQueue<>(16),
            new ThreadFactoryBuilder().setDaemon(false).setNameFormat("奇数打印-%s").build(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );
    private static final ExecutorService OU_EXECUTOR = new ThreadPoolExecutor(2, 2,
            2, TimeUnit.SECONDS, new LinkedBlockingQueue<>(16),
            new ThreadFactoryBuilder().setDaemon(false).setNameFormat("偶数打印-%s").build(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static void main(String[] args) {
        // semaphoreFun();
        // synchronizedFun();
        // reentrantLockFun();
        volatileFun();

        JI_EXECUTOR.shutdown();
        OU_EXECUTOR.shutdown();
    }

    static volatile boolean volatileFlag = false;

    public static void volatileFun() {
        OU_EXECUTOR.submit(() -> {
            for (int i = 0; i <= 100; i += 2) {
                while (volatileFlag) {
                    // 让出时间片
                    Thread.yield();
                }
                System.out.println(Thread.currentThread().getName() + " " + i);
                volatileFlag = true;
            }
        });
        JI_EXECUTOR.submit(() -> {
            for (int i = 1; i <= 100; i += 2) {
                while (!volatileFlag) {
                    // 让出时间片
                    Thread.yield();
                }
                System.out.println(Thread.currentThread().getName() + " " + i);
                volatileFlag = false;
            }
        });
    }

    public static void reentrantLockFun() {
        final Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        OU_EXECUTOR.submit(() -> {
            for (int i = 0; i <= 100; i += 2) {
                lock.lock();
                try {
                    while (flag) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " " + i);
                    flag = true;
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }
        });

        JI_EXECUTOR.submit(() -> {
            for (int i = 1; i <= 100; i += 2) {
                lock.lock();
                try {
                    while (!flag) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " " + i);
                    flag = false;
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }
        });
    }


    public static boolean flag = false;

    public static void synchronizedFun() {
        Object obj = new Object();

        OU_EXECUTOR.submit(() -> {
            synchronized (obj) {
                for (int i = 0; i <= 100; i += 2) {
                    while (flag) {
                        // 阻塞，等待其它线程执行
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    flag = true;
                    System.out.println(Thread.currentThread().getName() + " " + i);
                    obj.notifyAll();
                }
            }
        });
        JI_EXECUTOR.submit(() -> {
            synchronized (obj) {
                for (int i = 1; i <= 100; i += 2) {
                    while (!flag) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    flag = false;
                    System.out.println(Thread.currentThread().getName() + " " + i);
                    obj.notifyAll();
                }
            }
        });
    }

    public static void semaphoreFun() {
        Semaphore semaphoreA = new Semaphore(1);
        Semaphore semaphoreB = new Semaphore(0);
        OU_EXECUTOR.submit(() -> {
            for (int i = 0; i <= 100; i += 2) {
                try {
                    semaphoreA.acquire();
                    System.out.println(Thread.currentThread().getName() + " " + i);
                    semaphoreB.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        JI_EXECUTOR.submit(() -> {
            for (int i = 1; i <= 100; i += 2) {
                try {
                    semaphoreB.acquire();
                    System.out.println(Thread.currentThread().getName() + " " + i);
                    semaphoreA.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
