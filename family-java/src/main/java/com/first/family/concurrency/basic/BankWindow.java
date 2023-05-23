package com.first.family.concurrency.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description: 银行窗口出号
 * @author: cuiweiman
 * @date: 2023/2/17 14:47
 * @see BankCall
 */
public class BankWindow implements Runnable {

    private static Integer TICKET_NUMBER = 500;
    private static final List<Integer> COUNT_LIST = new ArrayList<>();


    static ReentrantReadWriteLock.WriteLock writeLock = new ReentrantReadWriteLock(true).writeLock();

    @Override
    public void run() {
        while (true) {
            writeLock.lock();
            try {
                if (TICKET_NUMBER > 0) {
                    COUNT_LIST.add(TICKET_NUMBER--);
                    System.out.println(Thread.currentThread().getName());
                } else {
                    break;
                }
            } finally {
                if (writeLock.isHeldByCurrentThread()) {
                    writeLock.unlock();
                }
            }
        }
        System.out.println(Thread.currentThread().getName() + "  " + COUNT_LIST.size()
                + " " + Thread.currentThread().getPriority());
    }

    /*@Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (TICKET_NUMBER > 0) {
                    COUNT_LIST.add(TICKET_NUMBER--);
                    System.out.println(Thread.currentThread().getName());
                } else {
                    break;
                }

            }
        }
        System.out.println(Thread.currentThread().getName() + "  " + COUNT_LIST.size());
    }*/

}
