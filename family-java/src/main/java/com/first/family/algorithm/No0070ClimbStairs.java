package com.first.family.algorithm;

import java.util.HashMap;

/**
 * LeetCode70 爬楼梯/斐波那契数列
 *
 * @description: <a href="https://leetcode.cn/problems/climbing-stairs/">LeetCode70 爬楼梯</a>
 * @author: cuiweiman
 * @date: 2023/8/29 16:28
 */
public class No0070ClimbStairs {

    public static void main(String[] args) {
        No0070ClimbStairs demo = new No0070ClimbStairs();
        int climbStairs = demo.climbStairs(45);
        System.out.println(climbStairs);
        int climbStairs2 = demo.climbStairs2(45);
        System.out.println(climbStairs2);
    }

    public static HashMap<Integer, Integer> map = new HashMap<>();

    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else if (n >= 3) {
            if (map.get(n) != null) {
                // 优化重复计算的部分，原本时间复杂度为 O(n^2) 优化为 O(n)
                return map.get(n);
            } else {
                int val = climbStairs(n - 1) + climbStairs(n - 2);
                map.put(n, val);
                return val;
            }
        }
        return 0;
    }

    public int climbStairs2(int n) {
        if (n <= 2) {
            return n;
        }
        int a = 1;
        int b = 2;
        int result = 0;
        for (int i = 3; i <= n; i++) {
            result = a + b;
            a = b;
            b = result;
        }
        return result;
    }
}
