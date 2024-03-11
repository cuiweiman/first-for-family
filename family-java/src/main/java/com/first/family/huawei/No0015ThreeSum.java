package com.first.family.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/3sum/">15. 三数之和</a>
 * <p>
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，
 * 同时还满足 nums[i] + nums[j] + nums[k] == 0。
 * 请你返回所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 示例 1：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 解释：
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
 * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
 * 注意，输出的顺序和三元组的顺序并不重要。
 * <p>
 * 示例 2：
 * 输入：nums = [0,1,1]
 * 输出：[]
 * 解释：唯一可能的三元组和不为 0 。
 * <p>
 * 示例 3：
 * 输入：nums = [0,0,0]
 * 输出：[[0,0,0]]
 * 解释：唯一可能的三元组和为 0 。
 *
 * @description: 15. 三数之和等于0
 * @author: cuiweiman
 * @date: 2024/3/6 11:46
 */
public class No0015ThreeSum {
    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        No0015ThreeSum demo = new No0015ThreeSum();
        List<List<Integer>> result1 = demo.threeSum(nums);
        System.out.println(result1);
    }


    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                // 当 元素 大于0 时，排序好的数组中已经不存在满足条件的元素了
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                // 跳过重复元素
                continue;
            }
            int a = i + 1;
            int b = nums.length - 1;
            while (a < b) {
                int sum = nums[i] + nums[a] + nums[b];
                if (sum > 0) {
                    // b--，并跳过重复元素
                    while (a < b && nums[b] == nums[--b]) {
                    }
                } else if (sum < 0) {
                    // a++，并跳过重复元素
                    while (a < b && nums[a] == nums[++a]) {
                    }
                } else {
                    result.add(new ArrayList<>(Arrays.asList(nums[i], nums[a], nums[b])));
                    while (a < b && nums[b] == nums[--b]) {
                    }
                    while (a < b && nums[a] == nums[++a]) {
                    }
                }
            }
        }
        return result;
    }
}