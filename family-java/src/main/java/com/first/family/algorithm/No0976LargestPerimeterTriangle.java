package com.first.family.algorithm;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/largest-perimeter-triangle/">976. 三角形的最大周长</a>
 * <p>
 * 三角形的三边特性：
 * a+b>c && a+c>b && b+c>a
 *
 * @description: 976. 三角形的最大周长
 * @author: cuiweiman
 * @date: 2023/12/15 11:54
 */
public class No0976LargestPerimeterTriangle {
    public static void main(String[] args) {
        No0976LargestPerimeterTriangle demo = new No0976LargestPerimeterTriangle();
        // int[] array = {1, 2, 2};
        // int[] array = {1, 2, 1, 10};
        int[] array = {2, 1, 2};
        System.out.println(demo.largestPerimeter(array));
        System.out.println(demo.largestPerimeterForce(array));
    }

    /**
     * 贪心算法？
     */
    public int largestPerimeter(int[] nums) {
        Arrays.sort(nums);
        int largest = 0;
        for (int i = nums.length - 1; i-2 >= 0; i--) {
            // 直接比较 最大边 与 两个小边的和，满足即可以组成三角形
            if (nums[i - 2] + nums[i - 1] > nums[i]) {
                largest = Math.max(largest, nums[i - 2] + nums[i - 1] + nums[i]);
            }
        }
        return largest;
    }

    /**
     * 暴力破解——超时
     */
    public int largestPerimeterForce(int[] nums) {
        int largest = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] > nums[k] && nums[i] + nums[k] > nums[j] && nums[j] + nums[k] > nums[i]) {
                        int temp = nums[i] + nums[j] + nums[k];
                        largest = Math.max(largest, temp);
                    }
                }
            }
        }
        return largest;
    }
}
