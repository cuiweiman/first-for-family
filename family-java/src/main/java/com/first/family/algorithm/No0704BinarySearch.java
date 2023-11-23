package com.first.family.algorithm;

/**
 * <a href="https://leetcode.cn/problems/binary-search/">二分查找</a>
 *
 * @description: 二分查找
 * @author: cuiweiman
 * @date: 2023/11/21 11:54
 */
public class No0704BinarySearch {
    public static void main(String[] args) {
        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 9;
        No0704BinarySearch demo = new No0704BinarySearch();
        int search = demo.search(nums, target);
        System.out.println(search);
        int search2 = demo.search2(nums, target);
        System.out.println(search2);
    }

    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int ptr = (left + right) / 2;
            if (nums[ptr] < target) {
                left = ptr + 1;
            } else if (nums[ptr] > target) {
                right = ptr - 1;
            } else {
                return ptr;
            }
        }
        return -1;
    }

    public int search2(int[] nums, int target) {
        return doSearch(nums, target, 0, nums.length - 1);
    }

    private int doSearch(int[] nums, int target, int left, int right) {
        if (left <= right) {
            int ptr = (left + right) / 2;
            if (nums[ptr] > target) {
                return doSearch(nums, target, left, ptr - 1);
            } else if (nums[ptr] < target) {
                return doSearch(nums, target, ptr + 1, right);
            } else {
                return ptr;
            }
        } else {
            return -1;
        }
    }
}
