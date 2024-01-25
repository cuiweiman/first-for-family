package com.first.family.review;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/12/12 10:02
 */
public class 数字计算 {
    public static void main(String[] args) {
        数字计算 demo = new 数字计算();
        System.out.println("平方根 除2: " + demo.平方根(110));
        System.out.println("平方根 牛顿迭代: " + demo.平方根2(110));
        System.out.println("斐波那契 循环: " + demo.斐波那契(8));
        System.out.println("斐波那契 迭代: " + demo.斐波那契迭代(8));
        System.out.println("排列硬币 暴力: " + demo.排列硬币(10));
        System.out.println("排列硬币 二分: " + demo.排列硬币2(10));
    }

    /**
     * 计算x的平方根：不使用sqrt(x)函数，返回平方根的整数部分
     * 二分查找发
     */
    public int 平方根(int x) {
        int index = -1, left = 0, right = x / 2;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (mid * mid <= x) {
                index = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return index;
    }

    /**
     * 牛顿迭代：查找平方根的这个数部分
     */
    public int 平方根2(int x) {
        return (int) newton(x / 2, x);
    }

    public double newton(double i, int x) {
        double res = (i + x / i) / 2;
        if (res == i) {
            return i;
        }
        return newton(res, x);
    }

    /**
     * 从 1 开始
     * 1-1，2-1，3-2，4-3，5-5，6-8，7-13，8-21
     */
    public int 斐波那契(int n) {
        if (n < 3) {
            return 1;
        }
        int x = 1, y = 1;
        int result = 0;
        for (int i = 3; i <= n; i++) {
            result = x + y;
            x = y;
            y = result;
        }
        return result;
    }

    public int 斐波那契迭代(int n) {
        if (n < 3) {
            return 1;
        }
        return 斐波那契迭代(n - 1) + 斐波那契迭代(n - 2);
    }

    /**
     * 排列硬币
     * 总共有n枚硬币，将它们摆成一个阶梯形装，第k行必须有k枚硬币。
     * 给定一个数字n，找出可形成完整阶梯的总行数。
     * n是一个32位有符号的非负整数
     * 例如 n = 6,
     * <pre class="code">
     * k=3 ***
     * k=2 **
     * k=1 *
     * </pre>
     */
    public int 排列硬币(int n) {
        int count = 0;
        for (int i = 1; i <= n; i++) {
            count++;
            n -= count;
        }
        return count;
    }

    public int 排列硬币2(int n) {
        // 1+2+……+k == n
        // k+k^2 = 2n
        // 查找一个k值，该k值满足 k+k^2==2n
        int left = 0, right = n;
        int target = 2 * n;
        while (left <= right) {
            int mid = (left + right) / 2;
            int temp = (mid + mid * mid);
            if (temp > target) {
                right = mid - 1;
            } else if (temp < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

}
