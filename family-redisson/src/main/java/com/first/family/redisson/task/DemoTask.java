package com.first.family.redisson.task;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @description: 定时任务
 * @author: cuiweiman
 * @date: 2023/6/5 15:50
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DemoTask {
    private final RedissonClient redissonClient;

    private static final String LOCK_KEY = "DemoTask:Lock";

    @Scheduled(cron = "0/30 * * * * ?")
    public void getLock1() {
        RLock lock = redissonClient.getLock(LOCK_KEY);
        try {
            if (lock.tryLock(2, 10, TimeUnit.SECONDS)) {
                System.out.println("Lock-1 获得锁");
                printTime();
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Lock-1 执行结束，即将自动释放锁");
            } else {
                System.out.println("Lock-1 获得锁失败");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("Lock-1 手动 释放锁");
            }
        }
        splitMethod();
    }

    // @Scheduled(cron = "0/30 * * * * ?")
    public void getLock2() {
        RLock lock = redissonClient.getLock(LOCK_KEY);
        try {
            if (lock.tryLock(2, 10, TimeUnit.SECONDS)) {
                System.out.println("Lock-2 获得锁");
                printTime();
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Lock-2 执行结束，即将自动释放锁");
            } else {
                System.out.println("Lock-2 获得锁失败");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("Lock-2 手动 释放锁");
            }
        }
        splitMethod();
    }

    // @Scheduled(cron = "0/30 * * * * ?")
    public void getLock3() {
        RLock lock = redissonClient.getLock(LOCK_KEY);
        try {
            if (lock.tryLock(2, 10, TimeUnit.SECONDS)) {
                System.out.println("Lock-3 获得锁");
                printTime();
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Lock-3 执行结束，即将自动释放锁");
            } else {
                System.out.println("Lock-3 获得锁失败");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("Lock-3 手动 释放锁");
            }
        }
        splitMethod();
    }

    // @Scheduled(cron = "0/30 * * * * ?")
    public void getLock4() {
        RLock lock = redissonClient.getLock(LOCK_KEY);
        try {
            if (lock.tryLock(2, 10, TimeUnit.SECONDS)) {
                System.out.println("Lock-4 获得锁");
                printTime();
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Lock-4 执行结束，即将自动释放锁");
            } else {
                System.out.println("Lock-4 获得锁失败");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("Lock-4 手动 释放锁");
            }
        }
        splitMethod();
    }

    // @Scheduled(cron = "0/30 * * * * ?")
    public void getLock5() {
        RLock lock = redissonClient.getLock(LOCK_KEY);
        try {
            if (lock.tryLock(2, 10, TimeUnit.SECONDS)) {
                System.out.println("Lock-5 获得锁");
                printTime();
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Lock-5 执行结束，即将自动释放锁");
            } else {
                System.out.println("Lock-5 获得锁失败");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("Lock-5 手动 释放锁");
            }
        }
        splitMethod();
    }

    // @Scheduled(cron = "0/30 * * * * ?")
    public void getLock6() {
        RLock lock = redissonClient.getLock(LOCK_KEY);
        try {
            if (lock.tryLock(2, 10, TimeUnit.SECONDS)) {
                System.out.println("Lock-6 获得锁");
                printTime();
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Lock-6 执行结束，即将自动释放锁");
            } else {
                System.out.println("Lock-6 获得锁失败");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("Lock-6 手动 释放锁");
            }
        }
        splitMethod();
    }

    private void splitMethod() {
        System.out.println("********************************************");
    }

    private void printTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String format = now.format(dateTimeFormatter);
        System.out.println(format);
    }

}
