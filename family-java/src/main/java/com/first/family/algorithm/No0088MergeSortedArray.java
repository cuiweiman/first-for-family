package com.first.family.algorithm;

import java.util.Arrays;

/**
 * merge-sorted-array
 *
 * @description: <a href="https://leetcode.cn/problems/merge-sorted-array/">LeetCode88 合并两个有序数组</a>
 * @author: cuiweiman
 * @date: 2023/8/30 10:52
 */
public class No0088MergeSortedArray {
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        // nums1 中非 0 元素的，nums1.length=m+n
        int m = 3;
        int[] nums2 = {2, 5, 6};
        int n = nums2.length;

        No0088MergeSortedArray demo = new No0088MergeSortedArray();
        demo.merge3(nums1, m, nums2, n);
        System.out.println(Arrays.toString(nums1));
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < n; i++) {
            nums1[i + m] = nums2[i];
        }
        Arrays.sort(nums1);
    }

    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, m, n);
        // 快排时间复杂度 O(nlogn)
        Arrays.sort(nums1);
    }

    public void merge3(int[] nums1, int m, int[] nums2, int n) {
        // 从两个数组最大下标开始比较，谁最大，放在后面即可。时间复杂度 O(m+n)
        int p1 = m - 1;
        int p2 = n - 1;
        while (p1 >= 0 && p2 >= 0) {
            nums1[p1 + p2 + 1] = nums1[p1] <= nums2[p2] ? nums2[p2--] : nums1[p1--];
        }
        while (p2 >= 0) {
            nums1[p1 + p2 + 1] = nums2[p2--];
        }
    }

}
