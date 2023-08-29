package com.first.family.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode1 两数之和, 只会存在一个有效答案
 *
 * @description: <a href="https://leetcode.cn/problems/two-sum/">LeetCode1 两数之和</a>
 * @author: cuiweiman
 * @date: 2023/8/29 17:14
 */
public class No0001TwoSum {

    public static void main(String[] args) {
        No0001TwoSum demo = new No0001TwoSum();
        /*int[] nums = {2, 7, 11, 15};
        int target = 9;*/
        int[] nums = {3, 2, 4};
        int target = 6;

        int[] twoSum = demo.twoSum(nums, target);
        System.out.println(Arrays.toString(twoSum));
        int[] twoSum2 = demo.twoSum2(nums, target);
        System.out.println(Arrays.toString(twoSum2));
    }

    public int[] twoSum(int[] nums, int target) {
        // 因数组中可能存在相同元素，因此可能存在重复计算，可以优化。时间复杂度 O(n^2)
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int another = target - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == another) {
                    res[0] = i;
                    res[1] = j;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * key=nums[i],value=i
     */
    Map<Integer, Integer> map = new HashMap<>();

    public int[] twoSum2(int[] nums, int target) {
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int another = target - nums[i];
            if (map.containsKey(another)) {
                res[0] = map.get(another);
                res[1] = i;
            }
            // 缓存数组值-下标
            map.put(nums[i], i);
        }
        return res;
    }
}
