package com.first.family.algorithm.lchot100;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/combination-sum/?envType=study-plan-v2&envId=top-100-liked">39. 组合总和</a>
 *
 * @description: 39. 组合总和
 * @author: cuiweiman
 * @date: 2024/3/29 23:22
 */
public class No0039CombinationSum {
    public static void main(String[] args) {
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        No0039CombinationSum demo = new No0039CombinationSum();
        List<List<Integer>> result = demo.combinationSum(candidates, target);
        System.out.println(result);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        dfs(candidates, 0, target, combine, result);
        return result;
    }

    public void dfs(int[] candidates, int index, int target, List<Integer> combine, List<List<Integer>> result) {
        if (index == candidates.length) {
            return;
        }
        if (target == 0) {
            result.add(new ArrayList<>(combine));
            return;
        }
        dfs(candidates, index + 1, target, combine, result);
        if (target - candidates[index] >= 0) {
            combine.add(candidates[index]);
            dfs(candidates, index, target - candidates[index], combine, result);
            combine.remove(combine.size() - 1);
        }
    }
}
