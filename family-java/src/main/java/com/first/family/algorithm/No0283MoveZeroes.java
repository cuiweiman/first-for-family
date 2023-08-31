package com.first.family.algorithm;

import java.util.Arrays;

/**
 * move-zeroes
 *
 * @description: <a href="https://leetcode.cn/problems/move-zeroes/">移动零</a>
 * @author: cuiweiman
 * @date: 2023/8/31 11:11
 */
public class No0283MoveZeroes {

    public static void main(String[] args) {
        int[] array = {0, 1, 0, 3, 12};
        No0283MoveZeroes demo = new No0283MoveZeroes();
        demo.moveZeroes(array);
        System.out.println(Arrays.toString(array));
    }

    public void moveZeroes(int[] nums) {
        int noZeroIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            // 将非 0 的元素迁移到数组前列，那么从 noZeroIndex 开始，之后的全为 0
            if (nums[i] != 0) {
                nums[noZeroIndex] = nums[i];
                noZeroIndex++;
            }
        }
        while (noZeroIndex < nums.length) {
            nums[noZeroIndex] = 0;
            noZeroIndex++;
        }
    }
}
