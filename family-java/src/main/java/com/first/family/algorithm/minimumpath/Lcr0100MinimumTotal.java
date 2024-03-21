package com.first.family.algorithm.minimumpath;

import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/IlPe0q/description/">LCR 100. 三角形最小路径和</a>
 * <p>
 * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
 * 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
 * 也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
 * <p>
 * eg1:
 * 输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
 * 输出：11
 * 解释：如下面简图所示：
 * *    2
 * *   3 4
 * *  6 5 7
 * * 4 1 8 3
 * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
 * <p>
 * f[i][j] =
 * j == 0: f[i-1][0] + c[i][0];   // 左边缘，没有f[i-1][j-1], 只有 f[i-1][j]
 * j == i: f[i-1][j-1] + c[i][j]; // 右边缘，没有 f[i-1][j]，只有f[i-1][j-1]
 * otherwise: min(f[i-1][j], f[i-1][j-1]) + c[i][j];
 *
 * @description: LCR 100. 三角形最小路径和
 * @author: cuiweiman
 * @date: 2024/3/18 09:55
 */
public class Lcr0100MinimumTotal {
    public static void main(String[] args) {
        List<List<Integer>> triangle = List.of(
                List.of(2),
                List.of(3, 4),
                List.of(6, 5, 7),
                List.of(4, 1, 8, 3)
        );

        Lcr0100MinimumTotal demo = new Lcr0100MinimumTotal();
        int result = demo.minimumTotal(triangle);
        System.out.println(result);
        int result2 = demo.minimumTotal2(triangle);
        System.out.println(result2);
        int result3 = demo.minimumTotal3(triangle);
        System.out.println(result3);
    }

    private int minimumTotal3(List<List<Integer>> triangle) {
        int size = triangle.size();
        int[] dp = new int[size + 1];
        for (int i = size - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = triangle.get(i).get(j) + Math.min(dp[j], dp[j + 1]);
            }
        }
        return dp[0];
    }


    private int minimumTotal2(List<List<Integer>> triangle) {
        int size = triangle.size();
        int[] dp = new int[size + 1];
        for (int i = size - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0];
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int size = triangle.size();
        int[][] dp = new int[size][size];
        dp[0][0] = triangle.get(0).get(0);
        // 再根据三角形特性，逐行处理各个节点的位置和
        for (int i = 1; i < size; i++) {
            // 处理三角形左边缘
            dp[i][0] = dp[i - 1][0] + triangle.get(i).get(0);
            for (int j = 1; j < i; j++) {
                // 处理三角形的边缘节点
                dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - 1]) + triangle.get(i).get(j);
            }
            // 处理三角形右边缘, 此时 i==j
            dp[i][i] = dp[i - 1][i - 1] + triangle.get(i).get(i);
        }
        // 获取最后一行的最小值
        return Arrays.stream(dp[size - 1]).min().orElse(-1);
    }

}
