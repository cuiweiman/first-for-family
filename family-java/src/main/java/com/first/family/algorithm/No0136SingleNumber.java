package com.first.family.algorithm;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/single-number/">只出现一次的数字</a>
 *
 * @description: 136 只出现一次的数字: 0^n=n, n^n=0;
 * @author: cuiweiman
 * @date: 2023/11/21 18:59
 */
public class No0136SingleNumber {
    public static void main(String[] args) {
        int[] nums = {4, 1, 2, 1, 2};
        No0136SingleNumber demo = new No0136SingleNumber();
        int singleNumber = demo.singleNumber(nums);
        System.out.println(singleNumber);
        int singleNumber2 = demo.singleNumber2(nums);
        System.out.println(singleNumber2);
    }

    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result = result ^ num;
        }
        return result;
    }


    public int singleNumber2(int[] nums) {
        // 等价于 Arrays.stream(nums).sum() ==> .reduce(0, (a, b) -> a + b);
        System.out.println(Arrays.stream(nums).reduce(0, Integer::sum));
        return Arrays.stream(nums).reduce(0, (a, b) -> a ^ b);
    }
}
