package com.first.family.algorithm;

/**
 * <a href="https://leetcode.cn/problems/champagne-tower/solutions/">799. 香槟塔</a>
 * <p>
 * 我们把玻璃杯摆成金字塔的形状，其中 第一层 有 1 个玻璃杯， 第二层 有 2 个，依次类推到第 100 层，每个玻璃杯 (250ml) 将盛有香槟。
 * <p>
 * 从顶层的第一个玻璃杯开始倾倒一些香槟，当顶层的杯子满了，任何溢出的香槟都会立刻等流量的流向左右两侧的玻璃杯。
 * 当左右两边的杯子也满了，就会等流量的流向它们左右两边的杯子，依次类推。（当最底层的玻璃杯满了，香槟会流到地板上）
 * <p>
 * 例如，在倾倒一杯香槟后，最顶层的玻璃杯满了。
 * 倾倒了两杯香槟后，第二层的两个玻璃杯各自盛放一半的香槟。
 * 在倒三杯香槟后，第二层的香槟满了 - 此时总共有三个满的玻璃杯。
 * 在倒第四杯后，第三层中间的玻璃杯盛放了一半的香槟，他两边的玻璃杯各自盛放了四分之一的香槟。
 * <p>
 * 示例 1:
 * 输入: poured(倾倒香槟总杯数) = 1, query_glass(杯子的位置数) = 1, query_row(行数) = 1
 * 输出: 0.00000
 * 解释: 我们在顶层（下标是（0，0））倒了一杯香槟后，没有溢出，因此所有在顶层以下的玻璃杯都是空的。
 * <p>
 * 示例 2:
 * 输入: poured(倾倒香槟总杯数) = 2, query_row(行数) = 1, query_glass(杯子的位置数) = 1
 * 输出: 0.50000
 * 解释: 我们在顶层（下标是（0，0）倒了两杯香槟后，有一杯量的香槟将从顶层溢出，位于（1，0）的玻璃杯和（1，1）的玻璃杯平分了这一杯香槟，
 * 所以每个玻璃杯有一半的香槟。
 * <p>
 * 示例 3:
 * 输入: poured = 100000009, query_row = 33, query_glass = 17
 * 输出: 1.00000
 *
 * @description: 799. 香槟塔
 * @author: cuiweiman
 * @date: 2024/3/21 14:20
 */
public class No0799ChampagneTower {
    public static void main(String[] args) {
        /*int poured = 100000009;
        int queryRow = 33;
        int queryGlass = 17;*/
       /* int poured = 2;
        int queryRow = 1;
        int queryGlass = 1;*/
        // 0.1875
        int poured = 25;
        int queryRow = 6;
        int queryGlass = 1;
        No0799ChampagneTower demo = new No0799ChampagneTower();
        double v = demo.champagneTower(poured, queryRow, queryGlass);
        System.out.println(v);
    }

    public double champagneTower(int poured, int query_row, int query_glass) {
        double[][] dp = new double[query_row + 2][query_row + 2];
        dp[0][0] = poured;
        for (int i = 0; i <= query_row; i++) {
            for (int j = 0; j <= i; j++) {
                // 这个杯子可以左右溢出多少,若结果为负，表示不够溢出
                double overflow = (dp[i][j] - 1.0) / 2;
                if (overflow > 0) {
                    // 这个杯子有溢出，那么计算下溢出到下一行两个杯子的分量，注意需要累加
                    dp[i + 1][j] += overflow;
                    dp[i + 1][j + 1] += overflow;
                }
            }
        }
        // 一杯最大数值为1
        return Math.min(1, dp[query_row][query_glass]);
    }

}
