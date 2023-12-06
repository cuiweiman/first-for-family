package com.first.family.algorithm;

import java.util.HashMap;

/**
 * <a href="https://leetcode.cn/problems/climbing-stairs/">力扣 70 爬楼梯</a>
 * <a href="https://leetcode.cn/problems/fibonacci-number/">力扣 509 斐波那契</a>
 * <a href="https://leetcode.cn/problems/fei-bo-na-qi-shu-lie-lcof/">LCR 126 斐波那契</a>
 *
 * @description: LeetCode70 爬楼梯/斐波那契数列
 * @author: cuiweiman
 * @date: 2023/8/29 16:28
 */
public class No0070ClimbStairs {

    public static void main(String[] args) {
        int num = 2;
        No0070ClimbStairs demo = new No0070ClimbStairs();
        int climbStairs = demo.climbStairs(num);
        System.out.println(climbStairs);
        int climbStairs2 = demo.climbStairs2(num);
        System.out.println(climbStairs2);
        int climbStairs3 = demo.climbStairs3(num);
        System.out.println(climbStairs3);
        System.out.println("============ 斐波那契 ============");
        // int fib = 9;
        int fib = 45;
        System.out.println(demo.fib(fib));
        System.out.println(demo.fib2(fib));
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

    /**
     * 动态规划解决爬楼梯问题
     *
     * @param n 台阶数
     * @return result
     */
    public int climbStairs3(int n) {
        if (n <= 2) {
            return n;
        }
        // 确定一个 dp 数组，下标表示 每一个台阶
        int[] dp = new int[n];
        // 初始化台阶数组
        dp[0] = 1;
        dp[1] = 2;
        // 总结台阶公式，遍历代入
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        // 得到每一个台阶的结果，并返回最终答案
        return dp[n - 1];
    }

    /**
     * 动态规划 斐波那契是从 0、1开始的
     */
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }
        // 确定一个 dp 数组，下标表示 每一个台阶
        int[] dp = new int[n + 1];
        // 初始化台阶数组
        dp[0] = 0;
        dp[1] = 1;
        // 总结台阶公式，遍历代入
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        // 得到每一个台阶的结果，并返回最终答案
        return dp[n];
    }

    public int fib2(int n) {
        if (n <= 1) {
            return n;
        }
        int a = 0;
        int b = 1;
        int res = 0;
        for (int i = 2; i <= n; i++) {
            res = a + b;
            a = b;
            b = res;
        }
        return res;
    }
}
