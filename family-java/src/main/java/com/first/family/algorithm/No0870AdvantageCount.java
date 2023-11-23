package com.first.family.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/advantage-shuffle/description/">优势洗牌</a>
 *
 * @description: 优势洗牌 / 田忌赛马
 * @author: cuiweiman
 * @date: 2023/11/20 10:20
 */
public class No0870AdvantageCount {
    public static void main(String[] args) {
        // int[] A = {12, 24, 8, 32};
        // int[] B = {13, 25, 32, 11};
        int[] A = {2, 0, 4, 1, 2};
        int[] B = {1, 3, 0, 0, 2};

        No0870AdvantageCount demo = new No0870AdvantageCount();
        int[] result = demo.advantageCount(A, B);
        System.out.println(Arrays.toString(result));
    }

    public int[] advantageCount(int[] nums1, int[] nums2) {
        // 排序
        int[] aSort = Arrays.copyOf(nums1, nums1.length);
        int[] bSort = Arrays.copyOf(nums2, nums2.length);
        Arrays.sort(aSort);
        Arrays.sort(bSort);
        LinkedList<Integer> useless = new LinkedList<>();
        // 记录 B 原始数组索引处，满足条件的 A 数组结果，因 B元素可能重复，因此使用链表
        Map<Integer, LinkedList<Integer>> bMap = new HashMap<>();
        for (int bEle : nums2) {
            bMap.put(bEle, new LinkedList<>());
        }
        // 遍历
        int bIndex = 0;
        for (int aEle : aSort) {
            if (aEle > bSort[bIndex]) {
                // 找到
                bMap.get(bSort[bIndex++]).addFirst(aEle);
            } else {
                // 找不到
                useless.addFirst(aEle);
            }
        }
        // 填充
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums2.length; i++) {
            if (bMap.get(nums2[i]).isEmpty()) {
                result[i] = useless.removeFirst();
            } else {
                result[i] = bMap.get(nums2[i]).removeFirst();
            }
        }
        return result;
    }
}
