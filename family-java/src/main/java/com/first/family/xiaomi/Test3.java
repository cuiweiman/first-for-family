package com.first.family.xiaomi;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @description: CPU 过高问题排查
 * @author: cuiweiman
 */
public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        System.out.printf("t-id %d, t-name %s", Thread.currentThread().getId(), Thread.currentThread().getName());
        int i = 100000;
        while (--i >= 0) {
            TimeUnit.MILLISECONDS.sleep(2);
            System.out.println(new Thread(() -> System.out.println("hello " + new Date())).getName());
        }
    }
}
