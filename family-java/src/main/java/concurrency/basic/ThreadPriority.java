package concurrency.basic;

/**
 * @description: 线程优先级测试
 * @author: cuiweiman
 * @date: 2023/2/19 16:18
 */
public class ThreadPriority {

    public static void main(String[] args) {
        Thread t1 = new Thread(new BankWindow(), "highest");
        Thread t2 = new Thread(new BankWindow(), "normal");
        Thread t3 = new Thread(new BankWindow(), "lowest");
        t1.setPriority(Thread.MAX_PRIORITY);
        t3.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
        t3.start();
    }

}
