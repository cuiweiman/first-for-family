package com.first.family.algorithm;

/**
 * <a href="https://leetcode.cn/problems/maximum-subarray/">53. 最大子数组和</a>
 * <p>
 * num = {-2, 1, -3, 4, -1, 2, 1, -5, 4}
 * dp = {-2, 1, -2, 4, 3, 5, 6, 1, 5}
 * 动态规划公式：dp[i] = Math.max(dp[i-1]+num[i], num[i])
 *
 * @description: 53. 最大子数组和
 * @author: cuiweiman
 * @date: 2023/12/4 11:12
 */
public class No0053MaxSubArray {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        No0053MaxSubArray demo = new No0053MaxSubArray();

        int maxSubArray = demo.maxSubArray(nums);
        System.out.println(maxSubArray);
        int maxSubArray2 = demo.maxSubArray2(nums);
        System.out.println(maxSubArray2);
        int maxSubArray3 = demo.maxSubArray3(nums);
        System.out.println(maxSubArray3);
    }

    /**
     * 动态规划 解决方案——简化数组
     */
    public int maxSubArray3(int[] nums) {
        int curr = nums[0];
        int res = curr;
        for (int i = 1; i < nums.length; i++) {
            curr = Math.max(nums[i], nums[i] + curr);
            res = Math.max(res, curr);
        }
        return res;
    }

    /**
     * 动态规划 解决方案
     */
    public int maxSubArray2(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int res = dp[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
            res = Math.max(res, dp[i]);
        }
        return res;
    }


    /**
     * 暴力求解 超时
     */
    public int maxSubArray(int[] nums) {
        int res = Integer.MIN_VALUE;
        int curr = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                curr = curr + nums[j];
                res = Math.max(curr, res);
            }
            curr = 0;
        }
        return res;
    }
}
