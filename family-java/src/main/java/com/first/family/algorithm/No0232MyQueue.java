package com.first.family.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <a href="https://leetcode.cn/problems/implement-queue-using-stacks/">用栈实现队列</a>
 *
 * @description: leetcode 232 用栈实现队列
 * @author: cuiweiman
 * @date: 2023/10/12 23:45
 */
public class No0232MyQueue {
    public static void main(String[] args) {
        MyQueue obj = new MyQueue();
        obj.push(1);
        obj.push(23);
        int param2 = obj.pop();
        System.out.println(param2);
        int param3 = obj.peek();
        System.out.println(param3);
        boolean param4 = obj.empty();
        System.out.println(param4);
    }
}

class MyQueue {

    /**
     * 一个输入栈，一个输出栈。压栈直接压入输入栈。
     * 出栈时若输出栈不为空，则直接输出；
     * 否则先将输入栈全部 pop 并 push 到输出栈，再从输出栈 popo
     */
    Deque<Integer> dequeInput;
    Deque<Integer> dequeOutput;

    public MyQueue() {
        dequeInput = new ArrayDeque<>();
        dequeOutput = new ArrayDeque<>();
    }

    public void push(int x) {
        dequeInput.push(x);
    }

    public int pop() {
        if (dequeOutput.isEmpty()) {
            while (!dequeInput.isEmpty()) {
                Integer pop = dequeInput.pop();
                dequeOutput.push(pop);
            }
        }
        return dequeOutput.pop();
    }

    public int peek() {
        if (dequeOutput.isEmpty()) {
            while (!dequeInput.isEmpty()) {
                Integer pop = dequeInput.pop();
                dequeOutput.push(pop);
            }
        }
        return dequeOutput.peek();
    }

    public boolean empty() {
        return dequeInput.isEmpty() && dequeOutput.isEmpty();
    }
}
