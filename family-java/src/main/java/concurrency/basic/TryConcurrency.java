package concurrency.basic;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/2/15 19:15
 */
public class TryConcurrency {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " 是一个 非守护线程");

        Thread t1 = new Thread(() -> readDb(), "非守护线程-T1");
        Thread t2 = new Thread(() -> writeFile(), "非守护线程-T2");
        t1.start();
        t2.start();
        System.out.println(Thread.currentThread().getName() + " 执行结束");
    }

    private static void writeFile() {
        try {
            System.out.println(Thread.currentThread().getName() + " begin write data to file");
            TimeUnit.SECONDS.sleep(10);
            TimeUnit.SECONDS.sleep(100);
            System.out.println(Thread.currentThread().getName() + " write data done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " write file complete");
    }

    private static void readDb() {
        try {
            System.out.println(Thread.currentThread().getName() + " begin read data from db");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + " read data done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " read data complete");
    }
}
