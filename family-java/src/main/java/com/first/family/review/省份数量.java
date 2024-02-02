package com.first.family.review;

/**
 * @description: 只要是相邻的省份，计为一个省份
 * @author: cuiweiman
 * @date: 2024/1/24 10:13
 */
public class 省份数量 {
    public static void main(String[] args) {
        省份数量 demo = new 省份数量();
        int[][] isConnected = {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}};
        int circleNum = demo.findCircleNumDfs(isConnected);
        System.out.println("circleNum = " + circleNum);
        /*int[][] isConnected2 = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        int circleNum2 = demo.findCircleNumDfs(isConnected2);
        System.out.println("circleNum2 = " + circleNum2);*/
    }

    private int findCircleNumDfs(int[][] isConnected) {
        int count = 0;
        boolean[] visited = new boolean[isConnected.length];
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited[i]) {
                // 统计所有相邻的省份，计为一个省份，都要标记为 已访问-true
                dfs(isConnected, visited, i);
                count++;
            }
        }
        return count;
    }

    public void dfs(int[][] isConnected, boolean[] visited, int i) {
        if (visited[i]) {
            return;
        }
        visited[i] = true;
        for (int j = 0; j < isConnected[i].length; j++) {
            if (!visited[j] && isConnected[i][j] == 1) {
                dfs(isConnected, visited, j);
                // visited[j] = true;
            }
        }
    }
}
