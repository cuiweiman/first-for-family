package com.first.family.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/permutations-ii/description/">47. 全排列 II</a>
 * <p>
 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 * <p>
 * 示例 1：
 * 输入：nums = [1,1,2]
 * 输出：
 * [[1,1,2],
 * [1,2,1],
 * [2,1,1]]
 * <p>
 * 示例 2：
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * @description: 47. 全排列 II
 * @author: cuiweiman
 * @date: 2024/3/9 18:06
 */
public class No0047PermuteUnique {
    public static void main(String[] args) {
        // int[] nums = {1, 1, 2};
        // int[] nums = {1, 2, 3};
        int[] nums = {3, 3, 0, 3};
        No0047PermuteUnique demo = new No0047PermuteUnique();
        List<List<Integer>> lists = demo.permuteUnique(nums);
        System.out.println(lists);
    }


    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        Arrays.sort(nums);
        doPermuteUnique(nums, result, 0, temp, visited);
        return result;
    }

    private void doPermuteUnique(int[] nums, List<List<Integer>> result, int i, List<Integer> temp, boolean[] visited) {
        if (i == nums.length) {
            result.add(new ArrayList<>(temp));
            return;
        }
        for (int j = 0; j < nums.length; j++) {
            if (visited[j] || (j > 0 && nums[j] == nums[j - 1] && !visited[j - 1])) {
                // 跳过 使用过的元素 以及 数组中的 重复元素
                continue;
            }
            temp.add(nums[j]);
            visited[j] = true;
            doPermuteUnique(nums, result, i + 1, temp, visited);
            visited[j] = false;
            temp.remove(i);
        }
    }
}
