package com.first.family.concurrency.jvm;

import java.time.Instant;

/**
 * 伪共享问题:
 * CPU 缓存使用的是 Cache Line 缓存行结构，一个缓存行 64 字节，可以存储 8 个8字节的Long类型。
 * 一个 CacheLine 可以存储多个变量，线程A修改了其中一个变量，会导致线程B中 CacheLine 失效，即使 线程B 不使用A修改的变量
 * <p>
 * 不解决伪共享问题 耗时 9,821 毫秒
 * <p>
 * 伪共享问题解决：
 * 1. 使用 @jdk.internal.vm.annotation.Contended (jdk9+) 注解 或 @sun.misc.Contended (jdk8)
 * 需要添加 VM Options -XX:-RestrictContended (使用此注解 后 耗时 3,921 毫秒)
 * 类注解 加上代表整个类的 每个变量 都会在 单独的 cache line 中
 * 2. 空间换时间，将一个 变量类型 放大到 64 字节，使 一个 变量 占用 一个 CacheLine 缓存行 (5311毫秒)
 *
 * <a href="https://zhuanlan.zhihu.com/p/458926355">知乎专栏</a>
 *
 * @description: CPU 伪共享 Demo
 * @author: cuiweiman
 * @date: 2023/12/29 15:07
 * @see PaddedAtomicLong
 * @see com.lmax.disruptor.RingBuffer
 */
public class FalseSharing implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        long start = Instant.now().toEpochMilli();
        runTest();
        long duration = Instant.now().toEpochMilli() - start;
        System.out.println("耗时 " + duration);
    }

    public static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public static final int NUM_THREADS = 4;
    public static final Long ITERATIONS = 500L * 1000L * 1000L;

    private final int arrayIndex;

    public FalseSharing(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            // 不断修改 数组值，造成伪共享问题
            // LONGS[arrayIndex].value = i;
            LONGS[arrayIndex].set(i);
        }
        System.out.println(Thread.currentThread().getName() + " " + i);
    }

    /**
     * Long 类型 展示 CPU 伪共享
     * <p>
     * 共享数组 变量
     * 各个线程 操作此数组中的 对应下标元素（线程数=数组长度=4）
     */
    // private static A[] LONGS = new A[NUM_THREADS];
    private static PaddedAtomicLong[] LONGS = new PaddedAtomicLong[NUM_THREADS];

    static {
        for (int i = 0; i < LONGS.length; i++) {
            // LONGS[i] = new A();
            LONGS[i] = new PaddedAtomicLong();
        }
    }


    /**
     * javac中添加下面的flag：
     * --add-exports java.base/jdk.internal.vm.annotation=ALL-UNNAMED
     */
    // @Contended
    public static final class A {
        // 线程之间 共享变量
        public volatile long value = 0L;
        // 不加 volatile 速度会比较快，不存在 MESI 导致的缓存失效，但不安全
        // public long value = 0L;
    }
}
