package com.first.family.huawei;

import java.util.Scanner;

/**
 * 有位客人来自异国，在该国使用m进制计数。该客人有个幸运数字n(n<m)，每次购物时，其总是喜欢计算本次支付的花费(折算为异国的价格后)中存在多少幸运数字。
 * 问：当其购买一个在我国价值k的产品时，其中包含多少幸运数字？
 * <p>
 * 第一行输入为 k, n, m。
 * k 表示 该客人购买的物品价值（以十进制计算的价格）
 * n 表示 该客人的幸运数字
 * m 表示 该客人所在国度的采用的进制
 * 输出幸运数字的个数，行末无空格。当输入非法内容时，输出0
 * <p>
 * 实例：
 * 输入 10 2 4
 * 输出 2
 *
 * @description:
 * @author: cuiweiman
 * @date: 2024/3/9 11:47
 */
public class History1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int k, n, m;
        while (in.hasNextInt()) {
            k = in.nextInt();
            n = in.nextInt();
            m = in.nextInt();
            int test = test(k, n, m);
            System.out.println("test=" + test);
            int test2 = test2(k, n, m);
            System.out.println("test2=" + test2);
        }
    }

    public static int test2(int k, int n, int m) {
        int count = 0;
        // String radix = "";
        while (k > 0) {
            if (k % m == n) {
                count++;
            }
            // radix += k % m;
            k = k / m;
        }
        /*for (int length = radix.length() - 1; length >= 0; length--) {
            System.out.print(radix.charAt(length));
        }*/
        System.out.println();
        return count;
    }

    public static int test(int k, int n, int m) {
        // 将 k 转为 m 进制
        String string = Integer.toString(k, m);
        // 判断 string 中有几个 n
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == n + 48) {
                count++;
            }
        }
        return count;
    }

}
