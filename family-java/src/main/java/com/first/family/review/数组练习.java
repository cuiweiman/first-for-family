package com.first.family.review;

import com.first.family.algorithm.common.MyUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/12/11 11:50
 */
public class 数组练习 {
    public static void main(String[] args) {
        数组练习 demo = new 数组练习();
        int[] array = {0, 1, 2, 2, 3, 3, 4};
        int 移除有序数组中的重复元素 = demo.移除有序数组中的重复元素(array);
        System.out.println("移除有序数组中的重复元素: " + 移除有序数组中的重复元素);

        int[] array2 = {1, 2, 7, 6, 5, 5};
        // int[] array2 = {1, 7, 3, 6, 5, 6};
        int 中心下标 = demo.中心下标(array2);
        System.out.println("寻找中心下标: " + 中心下标);

        int[] array3 = {-1, -2, -3, 1, 2, 3};
        System.out.println("三个数的最大乘积 排序: " + demo.三个数的最大乘积(array3));
        System.out.println("三个数的最大乘积 寻找最大最小值: " + demo.三个数的最大乘积2(array3));

        // 无序排列的数组
        int[] array4 = {1, 4, 3, 4, 6, 2};
        int target = 6;
        int[] twoSum = demo.twoSum(array4, target);
        System.out.println("两数之和——乱序排列的数组: " + Arrays.toString(twoSum));

        // 升序排列的数组
        int[] array5 = {1, 2, 3, 4, 6, 7};
        int target5 = 6;
        int[] twoSum2 = demo.twoSum2(array5, target5);
        System.out.println("两数之和——升序排列的数组: " + Arrays.toString(twoSum2));

        int[] arr6A = new int[8];
        for (int i = 1; i <= 5; i++) {
            arr6A[i - 1] = 2 * i - 1;
        }
        int[] arr6B = {2, 4, 6};
        int[] 合并两个有序数组 = demo.合并两个有序数组(arr6A, arr6B);
        System.out.println("合并两个有序数组: " + Arrays.toString(合并两个有序数组));

        int k7 = 4;
        int[] arr7 = {1, 12, -5, -6, 50, 3};
        double k个连续子数组的最大平均数 = demo.k个连续子数组的最大平均数(arr7, k7);
        System.out.println("k个连续子数组的最大平均数: " + k个连续子数组的最大平均数);

    }

    public int 移除有序数组中的重复元素(int[] array) {
        // 返回删除重复元素后的 数组长度
        if (array.length == 0) {
            return 0;
        }
        int i = 0;
        for (int j = 1; j < array.length; j++) {
            if (array[i] != array[j]) {
                i++;
                array[i] = array[j]; // 将不重复的元素放置在数组靠前下标
            }
        }
        int[] noRepeatArr = Arrays.copyOf(array, i);
        MyUtil.intArrPrint("移除有序数组后的数组", noRepeatArr);
        return i;
    }

    /**
     * 获取整数数组的中心下标。
     * 中心下标: 数组的一个下标，其左侧所有元素相加等于右侧所有元素相加的和(相加元素不包含元素自身)。
     * 如果数组不存在中心下标则返回-1，若有多个中心下标则返回最左边的那一个。
     * P.S. 中心下标可能出现在数组的两端，即整个数组相加等于0。
     * <p>
     * 解法思路：
     * 1. 计算数组的总和sum
     * 2. 遍历数组，逐个计算数组的和total，逐个递减sum，直到total和sum值相等，那么就是中心下标
     */
    public int 中心下标(int[] array) {
        int sum = Arrays.stream(array).sum();
        int total = 0;
        for (int i = 0; i < array.length; i++) {
            total += array[i];
            if (total == sum) {
                return i;
            }
            sum -= array[i];
        }
        return -1;
    }

    /**
     * 给定一个数组，筛选三个数字，保证其乘积是最大的。
     * <p>
     * 如果有负数，那么至少需要两个绝对值最大的负数。
     * 如果是正数，那么是三个最大的正数。
     * 因此只需要对比 最小数1*最小数2*最大数，以及最大数*最大数2*最大数3 的大小即可。
     * O(nlogn)
     */
    public int 三个数的最大乘积(int[] array) {
        Arrays.sort(array);
        int length = array.length;
        return Math.max(array[0] * array[1] * array[length - 1], array[length - 1] * array[length - 2] * array[length - 3]);
    }

    public int 三个数的最大乘积2(int[] array) {
        // 不通过排序获取最小的2个值和三个最大的值，而是通过一次遍历获取到
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;

        for (int i : array) {
            if (min1 > i) {
                // array[i] < min1
                min2 = min1;
                min1 = i;
            } else if (min2 > i) {
                // min1 < array[i] < min2
                min2 = i;
            }
            if (max1 < i) {
                // max1 < array[i]
                max3 = max2;
                max2 = max1;
                max1 = i;
            } else if (max2 < i) {
                // max2 < array[i] < max1
                max3 = max2;
                max2 = i;
            } else if (max3 < i) {
                // max3 < array[i] < max2 < max1
                max3 = i;
            }
        }
        return Integer.max(min2 * min1 * max1, max3 * max2 * max1);
    }


    /**
     * 两数之和
     * 一个整数数组，从数组中找出两个数，满足相加之和等于目标数。并以数组的形式返回两个数的下标
     * 数组元素只使用一次，且对应唯一答案
     */
    public int[] twoSum(int[] array, int target) {
        Map<Integer, Integer> map = new HashMap<>(array.length);
        for (int i = 0; i < array.length; i++) {
            int other = target - array[i];
            if (map.containsKey(other)) {
                return new int[]{map.get(other), i};
            } else {
                map.put(array[i], i);
            }
        }
        return new int[2];
    }

    /**
     * 两数之和2::升序数组 —— 双指针 之 左右指针
     * 一个升序排列的整数数组，从数组中找出两个数，满足相加之和等于目标数。并以数组的形式返回两个数的下标
     * 数组元素只使用一次，且对应唯一答案
     */
    public int[] twoSum2(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int sum = array[left] + array[right];
            if (sum > target) {
                right--;
            } else if (sum < target) {
                left++;
            } else {
                return new int[]{left, right};
            }
        }
        return new int[2];
    }

    /**
     * arr1.length = arr1的有效元素个数 + arr2的有效元素个数
     */
    public int[] 合并两个有序数组(int[] arr1, int[] arr2) {
        // A数组有效元素的最大下标
        int iA = arr1.length - arr2.length - 1;
        // B数组有效元素的最大下标
        int iB = arr2.length - 1;
        // 比较两个数，将大的值放在 A 数组的末尾
        int iC = arr1.length - 1;

        while (iA >= 0 && iB >= 0) {
            if (arr1[iA] <= arr2[iB]) {
                arr1[iC--] = arr2[iB--];
            } else {
                arr1[iC--] = arr1[iA--];
            }
        }
        return arr1;
    }

    /**
     * 下标连续的子数组最大平均数
     * 给一个整数数组，找出平均数最大，且长度为k的下标连续的子数组，并返回该最大平均数
     * 12.75
     * 滑动窗口思想
     */
    public double k个连续子数组的最大平均数(int[] array, int k) {
        int left = 0;
        int right = 0;
        double sum = 0d;
        double avg = 0d;
        while (right < array.length - 1) {
            if (right < k - 1) {
                sum += array[right];
                right++;
                continue;
            }
            sum += array[right];
            avg = Math.max(sum / k, avg);
            sum = sum - array[left];
            left++;
            right++;
        }
        return avg;
    }
}



















