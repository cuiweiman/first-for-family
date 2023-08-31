package com.first.family.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 448. 找到所有数组中消失的数字
 *
 * @description: <a href="https://leetcode.cn/problems/find-all-numbers-disappeared-in-an-array/">leetcode 448 找到所有数组中消失的数字</a>
 * @author: cuiweiman
 * @date: 2023/8/31 11:48
 */
public class No0448FindDisappearedNumbers {
    public static void main(String[] args) {
        int[] array = {4, 3, 2, 7, 8, 2, 3, 1};
        No0448FindDisappearedNumbers demo = new No0448FindDisappearedNumbers();
        System.out.println(demo.findDisappearedNumbers(array));
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        boolean[] judge = new boolean[nums.length + 1];
        for (int i = 1; i < judge.length; i++) {
            judge[nums[i - 1]] = true;
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < judge.length; i++) {
            if (!judge[i]) {
                list.add(i);
            }
        }
        return list;
    }
}
