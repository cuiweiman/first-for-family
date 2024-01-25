package com.first.family.algorithm;

/**
 * <a href="https://leetcode.cn/problems/longest-continuous-increasing-subsequence/">674. 最长连续递增序列</a>
 *
 * @description: 674. 最长连续递增序列
 * @author: cuiweiman
 * @date: 2023/12/15 15:17
 */
public class No0674LongestContinuousIncreasingSubsequence {
    public static void main(String[] args) {
        No0674LongestContinuousIncreasingSubsequence demo = new No0674LongestContinuousIncreasingSubsequence();
        // int[] array = {1, 3, 5, 4, 7};
        // int[] array = {1, 3, 5, 7};
        int[] array = {2, 2, 2, 2, 2};
        System.out.println(demo.findLengthOfLCIS(array));
    }

    public int findLengthOfLCIS(int[] nums) {
        int result = 1;
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                count++;
            } else {
                result = Math.max(result, count);
                count = 1;
            }
        }
        // 比较最后一次
        result = Math.max(result, count);
        return result;
    }
}
