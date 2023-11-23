package com.first.family.algorithm;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/counting-bits/">338. 比特位计数</a>
 *
 * @description: 338. 比特位计数
 * @author: cuiweiman
 * @date: 2023/11/21 19:56
 */
public class No0338CountBits {
    public static void main(String[] args) {
        int n = 21;
        System.out.println(Integer.bitCount(n));
        No0338CountBits demo = new No0338CountBits();
        int[] countBits = demo.countBits(n);
        System.out.println(Arrays.toString(countBits));
    }

    public int[] countBits(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            bits[i] = bits[i & (i - 1)] + 1;
        }
        return bits;
    }
}
