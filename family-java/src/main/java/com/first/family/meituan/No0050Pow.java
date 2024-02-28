package com.first.family.meituan;

/**
 * <a href="https://leetcode.cn/problems/powx-n/description/">50. Pow(x, n)</a>
 *
 * <p>
 * 实现 pow(x, n) ，即计算 x 的整数 n 次幂函数（即，xn ）。
 * <p>
 * 示例 1：
 * 输入：x = 2.00000, n = 10
 * 输出：1024.00000
 * <p>
 * 示例 2：
 * 输入：x = 2.10000, n = 3
 * 输出：9.26100
 * <p>
 * 示例 3：
 * 输入：x = 2.00000, n = -2
 * 输出：0.25000
 * 解释：2^-2 = (1/2)^2 = 1/4 = 0.25
 *
 * @description: 50. Pow(x, n)
 * @author: cuiweiman
 * @date: 2024/2/23 15:09
 */
public class No0050Pow {
    public static void main(String[] args) {
        double x = 3;
        int n = 3;
        No0050Pow demo = new No0050Pow();
        double myPow = demo.myPow(x, n);
        System.out.println(myPow);
        System.out.println(demo.mySqrt(4));
    }

    public double myPow(double x, int n) {
        long pow = n;
        return n > 0 ? doMyPow(x, pow) : 1 / doMyPow(x, -pow);
    }

    public double doMyPow(double x, long n) {
        if (n == 0) {
            return 1;
        }
        double y = doMyPow(x, n / 2);
        return n % 2 == 0 ? y * y : y * y * x;
    }

    public int mySqrt(int x) {
        int left = 0;
        int right = x / 2;
        int result = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (mid * mid <= x) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }
}
