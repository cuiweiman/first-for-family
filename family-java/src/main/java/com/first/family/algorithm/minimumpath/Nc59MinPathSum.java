package com.first.family.algorithm.minimumpath;

/**
 * <a href="https://www.nowcoder.com/practice/7d21b6be4c6b429bb92d219341c4f8bb?tpId=196">NC59 矩阵的最小路径和</a>
 * <a href="https://leetcode.cn/problems/0i0mDW/description/">LCR 099. 最小路径和</a>
 * <p>
 * 给定一个 n * m 的矩阵 a，从左上角开始每次只能向右或者向下走，最后到达右下角的位置，
 * 路径上所有的数字累加起来就是路径和，输出所有的路径中最小的路径和。
 * <p>
 * 数据范围: 1≤n,m≤2000，矩阵中任意值都满足 0≤a(i,j)≤100
 * 要求：时间复杂度 O(nm)
 * <p>
 * 例如：当输入[[1,3,5,9],[8,1,3,4],[5,0,6,1],[8,8,4,0]]时，对应的返回值为12，
 * 所选择的最小累加和路径如下图所示：
 * 1 3 5 9
 * 8 1 3 4
 * 5 0 6 1
 * 8 8 4 0
 * <p>
 * eg1
 * 输入 [[1,3,5,9],[8,1,3,4],[5,0,6,1],[8,8,4,0]]
 * 输出 12
 * <p>
 * eg2
 * 输入 [[1,2,3],[1,2,3]]
 * 输出 7
 *
 * @description: NC59 矩阵的最小路径和
 * @author: cuiweiman
 * @date: 2024/3/17 12:08
 */
public class Nc59MinPathSum {

    public static void main(String[] args) {
        int[][] matrix = {{1, 3, 5, 9}, {8, 1, 3, 4}, {5, 0, 6, 1}, {8, 8, 4, 0}};
        // int[][] matrix = {{1, 2, 3,}, {1, 2, 3}};
        Nc59MinPathSum demo = new Nc59MinPathSum();
        int res = demo.minPathSum(matrix);
        System.out.println(res);
        int res2 = demo.minPathSum2(matrix);
        System.out.println(res2);

        System.out.println("======= old =======");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.println();
        }
        int[][] reverse = demo.reverse(matrix);
        System.out.println("======= reverse =======");
        for (int i = 0; i < reverse.length; i++) {
            for (int j = 0; j < reverse[i].length; j++) {
                System.out.printf("%d ", reverse[i][j]);
            }
            System.out.println();
        }
    }

    public int minPathSum2(int[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        int[][] dp = new int[height][width];
        dp[0][0] = matrix[0][0];
        for (int i = 1; i < width; i++) {
            // 处理 首行
            dp[0][i] = matrix[0][i] + dp[0][i - 1];
        }
        for (int i = 1; i < height; i++) {
            // 处理 首列
            dp[i][0] = matrix[i][0] + dp[i - 1][0];
            for (int j = 1; j < width; j++) {
                dp[i][j] = matrix[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[height - 1][width - 1];
    }

    public int minPathSum(int[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        // 从 (0,0) 开始，存储每个位置的最短路径和。
        int[][] dp = new int[height][width];
        dp[0][0] = matrix[0][0];
        for (int i = 1; i < matrix.length; i++) {
            // 记录第一列的最短路径和，向下走
            dp[i][0] = dp[i - 1][0] + matrix[i][0];
        }
        for (int i = 1; i < matrix[0].length; i++) {
            // 记录第一行的最短路径和，向右走
            dp[0][i] = dp[0][i - 1] + matrix[0][i];
        }
        // 遍历整个矩阵，计算非首行首列的最短路径位置
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                // 比较向下走和向右走，哪个最优
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + matrix[i][j];
            }
        }
        return dp[height - 1][width - 1];
    }

    public int[][] reverse(int[][] matrix) {
        int x = 0;
        int y = 0;
        for (int i = matrix.length - 1; i >= x; i--) {
            for (int j = matrix[i].length - 1; j >= 0; j--) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[x][y];
                matrix[x][y] = temp;
                y++;
            }
            x++;
            y = 0;
        }
        return matrix;
    }

}
