package com.first.family.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 给定一个矩阵，包含N*M个整数，和一个包含K个整数的数组。
 * 现在要求在这个矩阵中找一个宽度最小的子短阵，要求子矩阵包含数组中所有的整数。
 * <p>
 * 输入描述
 * 第一行输入两个正整数 N, M 表示矩阵大小。
 * 接下来 N 行 M列表示矩阵内容。
 * 下一行包含一个正整数K。
 * 下一行包含K个整数，表示所需包含的数组，K个整数可能存在重复数字， 所有输入数据小于 1000。
 * 输出包含一个整数，表示满足要求子矩阵的最小宽度，若找不到，输出-1.
 * <p>
 * 输入：
 * 2 5
 * 1 2 2 3 1
 * 2 3 2 3 2
 * 3
 * 1 2 3
 * 输出：
 * 2
 * 说明：
 * 短阵第0、1列包含了1、2、3
 * <p>
 * 输入：
 * 2 5
 * 1 2 2 3 1
 * 1 3 2 3 4
 * 3
 * 1 1 4
 * 输出：
 * 5
 * 说明：
 * 短阵第1、2、3、4、5列包含了1、1、4
 *
 * @description:
 * @author: cuiweiman
 * @date: 2024/3/9 13:01
 */
public class History3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int N = in.nextInt();
            int M = in.nextInt();
            int[][] edges = new int[N][M];
            for (int i = 0; i < edges.length; i++) {
                for (int j = 0; j < edges[i].length; j++) {
                    edges[i][j] = in.nextInt();
                }
            }
            int K = in.nextInt();
            int[] arr = new int[K];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = in.nextInt();
            }
            Integer res = shortEdges(N, M, edges, K, arr);
            System.out.println(res);
        }
    }

    public static Integer shortEdges(int N, int M, int[][] edges, int K, int[] arr) {
        List<Integer> list = new ArrayList<>();
        int max = 0;
        for (int i : arr) {
            list.add(i);
            max = Math.max(max, i);
        }
        int count = 0;
        // 所有输入的值都小于1000
        int[] visited = new int[max + 1];
        // N 行 M 列, 先遍历列
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                // arr 中可能存在重复数字
                if (list.contains(edges[j][i]) && visited[edges[j][i]] == 0) {
                    // 未被访问
                    visited[edges[j][i]] = 1;
                    count++;
                    if (count == K) {
                        return i + 1;
                    }
                }
            }
        }
        return -1;
    }


}
