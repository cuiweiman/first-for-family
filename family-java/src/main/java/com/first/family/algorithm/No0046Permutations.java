package com.first.family.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/permutations/description/">46. 全排列</a>
 * <p>
 * 回溯算法
 * https://leetcode.cn/problems/permutations/solutions/9914/hui-su-suan-fa-python-dai-ma-java-dai-ma-by-liweiw/
 * 深度优先遍历、递归、栈，它们三者的关系，它们背后统一的逻辑都是「后进先出」
 *
 * <p>
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 * <p>
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * <p>
 * 示例 2：
 * 输入：nums = [0,1]
 * 输出：[[0,1],[1,0]]
 * <p>
 * 示例 3：
 * 输入：nums = [1]
 * 输出：[[1]]
 *
 * @description: 46. 全排列
 * @author: cuiweiman
 * @date: 2024/3/9 18:21
 */
public class No0046Permutations {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        No0046Permutations demo = new No0046Permutations();
        List<List<Integer>> permute = demo.permute(nums);
        System.out.println(permute);
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length == 0) {
            return result;
        }
        boolean[] visited = new boolean[nums.length];
        List<Integer> path = new ArrayList<>();
        dfs(nums, nums.length, 0, path, visited, result);
        return result;
    }

    private void dfs(int[] nums, int length, int depth, List<Integer> path, boolean[] visited, List<List<Integer>> result) {
        if (length == depth) {
            // 需要重新 new 一个集合，如果直接添加 path 集合，回溯过程中，path 地址被清空，则 result 的元素 也会变成空集合
            result.add(new ArrayList<>(path));
            return;
        }
        for (int j = 0; j < length; j++) {
            // 跳过使用过的元素
            if (!visited[j]) {
                // 存放当前 j 元素
                path.add(nums[j]);
                // 标注 j 元素 访问状态
                visited[j] = true;

                dfs(nums, length, depth + 1, path, visited, result);
                // 回朔
                visited[j] = false;
                path.remove(path.size() - 1);
            }
        }
    }
}
