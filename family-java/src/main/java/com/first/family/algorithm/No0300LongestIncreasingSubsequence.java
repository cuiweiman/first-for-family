package com.first.family.algorithm;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/longest-increasing-subsequence/?envType=study-plan-v2&envId=top-100-liked">300. 最长递增子序列</a>
 * <p>
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 * <p>
 * 示例 1：
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 * <p>
 * 示例 2：
 * 输入：nums = [0,1,0,3,2,3]
 * 输出：4
 * <p>
 * 示例 3：
 * 输入：nums = [7,7,7,7,7,7,7]
 * 输出：1
 * <p>
 * 提示：
 * 1 <= nums.length <= 2500
 * -10^4 <= nums[i] <= 10^4
 * <p>
 * 进阶：
 * 你能将算法的时间复杂度降低到 O(n log(n)) 吗?
 *
 * @description: 300. 最长递增子序列
 * @author: cuiweiman
 * @date: 2024/3/25 20:43
 */
public class No0300LongestIncreasingSubsequence {
    public static void main(String[] args) {
        // int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        int[] nums = {1, 3, 6, 7, 9, 4, 10, 5, 6};
        No0300LongestIncreasingSubsequence demo = new No0300LongestIncreasingSubsequence();
        int syy = demo.syy(nums);
        System.out.println("syy=" + syy);
        int result = demo.lengthOfLIS(nums);
        System.out.println("result=" + result);
    }

    public int lengthOfLIS(int[] nums) {
        int length = nums.length;
        int[] dp = new int[length];
        dp[length - 1] = 1;
        for (int i = length - 2; i >= 0; i--) {
            int temp = 0;
            for (int j = i + 1; j < length; j++) {
                if (nums[i] < nums[j]) {
                    temp = Math.max(temp, dp[j]);
                }
            }
            dp[i] = temp + 1;
        }
        return Arrays.stream(dp).max().getAsInt();
    }

    public int syy(int[] nums) {
        int length = nums.length;
        // 存储当前的最大长度
        int[] dp = new int[length];
        dp[length - 1] = 1;
        for (int i = length - 2; i >= 0; i--) {
            int temp = 0;
            for (int j = i + 1; j < length; j++) {
                if (nums[j] > nums[i]) {
                    temp = Math.max(temp, dp[j]);
                }
            }
            dp[i] = temp + 1;
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}
