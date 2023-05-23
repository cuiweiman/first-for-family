package com.first.family.concurrency.basic;

/**
 * @description: 银行叫号
 * @author: cuiweiman
 * @date: 2023/2/17 14:47
 * @see BankWindow
 */
public class BankCall {
    public static void main(String[] args) {
        BankWindow window = new BankWindow();
        Thread thread1 = new Thread(window, "一号");
        Thread thread2 = new Thread(window, "二号");
        Thread thread3 = new Thread(window, "三号");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
