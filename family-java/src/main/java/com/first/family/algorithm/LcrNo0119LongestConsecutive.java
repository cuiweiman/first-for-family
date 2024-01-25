package com.first.family.algorithm;


import java.util.HashSet;
import java.util.Set;

/**
 * <a href="https://leetcode.cn/problems/WhsWhI/">LCR 119. 最长连续序列</a>
 *
 * @description: LCR 119. 最长连续序列
 * @author: cuiweiman
 * @date: 2023/12/15 15:35
 */
public class LcrNo0119LongestConsecutive {
    public static void main(String[] args) {
        LcrNo0119LongestConsecutive demo = new LcrNo0119LongestConsecutive();
        // int[] array = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        int[] array = {100, 4, 200, 1, 3, 2};
        System.out.println(demo.longestConsecutive(array));
        System.out.println(demo.longestConsecutive2(array));
    }

    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>(nums.length);
        for (int num : nums) {
            set.add(num);
        }
        int longest = 0;
        for (int num : nums) {
            if (set.contains(num - 1)) {
                continue;
            }
            int temp = 1;
            while (set.contains(num + 1)) {
                temp++;
                num++;
            }
            longest = Math.max(longest, temp);
        }
        return longest;
    }


    public int longestConsecutive2(int[] nums) {
        Set<Integer> set = new HashSet<>(nums.length);
        for (int num : nums) {
            set.add(num);
        }
        int longest = 0;
        for (int num : nums) {
            if (!set.contains(num - 1)) {
                // 判断 num 是不是 数组中连续序列 最小的，如果不是那么继续循环，确保从 连续序列的 最小元素 开始计算
                int temp = 1;
                while (set.contains(num + 1)) {
                    num = num + 1;
                    temp++;
                }
                longest = Math.max(longest, temp);
            }
        }
        return longest;
    }
}
