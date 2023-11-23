package com.first.family.algorithm;

/**
 * <a href="https://leetcode.cn/problems/hamming-distance/">461. 汉明距离</a>
 *
 * @description: 461. 汉明距离
 * @author: cuiweiman
 * @date: 2023/11/23 16:00
 * @see No0338CountBits 338. 比特位计数
 */
public class No0461HammingDistance {
    public static void main(String[] args) {
        int x = 1;
        int y = 4;
        No0461HammingDistance demo = new No0461HammingDistance();
        int hammingDistance = demo.hammingDistance(x, y);
        System.out.println(hammingDistance);
    }

    public int hammingDistance(int x, int y) {
        int distance = 0;
        for (int xor = x ^ y; xor != 0; xor &= (xor - 1)) {
            distance++;
        }
        return distance;
    }
}
