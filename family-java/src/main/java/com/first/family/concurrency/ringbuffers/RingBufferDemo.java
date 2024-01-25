package com.first.family.concurrency.ringbuffers;

/**
 * @description: 环形缓冲区 Java 简单实现
 * @author: cuiweiman
 * @date: 2023/12/22 10:27
 * @see #getPos(int) 取模运算替换成 位运算 7 % 4 == 7 & (4-1)
 * @see #RingBufferDemo 被余数最好是 2 的 n 次幂，位运算才有效
 */
public class RingBufferDemo {

    public static void main(String[] args) throws InterruptedException {
        // int length = (int) Math.pow(2, 3);
        RingBufferDemo ringBuffer = new RingBufferDemo(7);
        /*for (int i = 0; i < 10; i++) {
            System.out.printf("%b ", ringBuffer.put(i));
        }
        System.out.println("\n" + ringBuffer);
        for (int i = 0; i < 10; i++) {
            System.out.printf("%d ", ringBuffer.get());
        }
        System.out.println("\n" + ringBuffer);*/

        new Thread(() -> {
            for (int i = 0; i < 15; i++) {
                try {
                    Thread.sleep(50);
                    System.out.println(Thread.currentThread().getName() + ": " + i + "  " + ringBuffer.put(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Producer").start();

        new Thread(() -> {
            for (int i = 0; i < 15; i++) {
                try {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + "  " + ringBuffer.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者").start();

        Thread.currentThread().join(5000);
        System.out.println(ringBuffer);
    }

    /**
     * 环形缓冲区 最大容量
     */
    private final int length;

    /**
     * 环形缓冲区 数组， 长度为 length
     */
    private final Object[] data;

    /**
     * 环形缓冲区 写指针
     */
    private int writePos;

    /**
     * 环形缓冲区 读指针
     */
    private int readPos;

    static final int MAXIMUM_CAPACITY = 1 << 30;

    public RingBufferDemo(int length) {
        // 将输入的长度，转换为 2 的 n 次幂, >>> 无符号右移
        int n = -1 >>> Integer.numberOfLeadingZeros(length - 1);
        this.length = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        this.data = new Object[this.length];
    }

    /**
     * RingBuffer 是否 缓冲区满
     * 满： writePos == readPos + length
     *
     * @return true-满 不能再生产元素
     */
    public boolean isFull() {
        return this.writePos == this.readPos + this.length;
    }

    /**
     * RingBuffer 是否 缓冲区空
     * 空： writePos == readPos
     *
     * @retrun true-空 不能取出元素
     */
    public boolean isEmpty() {
        return this.writePos == this.readPos;
    }

    private int getPos(int currentPos) {
        return currentPos & (this.length - 1);
    }

    /**
     * 往 RingBuffer 中添加元素
     * 1. 如果 RingBuffer 满了，则直接返回 false
     * 2. 如果 RingBuffer 没有满，则往 RingBuffer 中添加元素
     * 3. 更新 RingBuffer 的 size
     * 4. 更新 RingBuffer 的 writePos
     * 5. 返回 true
     */
    public boolean put(Object obj) {
        // 更新 writePos, 如果达到最大length, 则从头开始写入, 循环更新
        if (isFull()) {
            // 实际开发中，可以抛出异常，或者返回一个默认值，或者引入拒绝策略，避免数据丢失
            return false;
        }
        this.data[getPos(this.writePos)] = obj;
        this.writePos++;
        return true;
    }

    /**
     * 从 RingBuffer 中取出元素
     * 1. 如果 RingBuffer 是空，则直接返回 null
     * 2. 如果 RingBuffer 没有空，则取出元素
     * 3. 更新 RingBuffer 的 size
     * 4. 更新 RingBuffer 的 readPos
     * 5. 返回元素
     *
     * @return 返回 RingBuffer 中元素
     */
    public Object get() {
        if (isEmpty()) {
            // 实际开发中，可以抛出异常，或者返回一个默认值
            return null;
        }
        Object obj = this.data[getPos(this.readPos)];
        // 更新 readPos, 如果达到最大length, 则从头开始读取, 循环更新
        this.readPos++;
        return obj;
    }

    @Override
    public String toString() {
        return "RingBufferDemo{" +
                "length=" + this.length +
                ", writePos=" + this.writePos +
                ", readPos=" + this.readPos +
                '}';
    }

    public String dataToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(this.data[i]).append(",");
        }
        return sb.toString();
    }

}
