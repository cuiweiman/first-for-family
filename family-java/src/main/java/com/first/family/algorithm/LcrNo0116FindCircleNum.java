package com.first.family.algorithm;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * <a href="https://leetcode.cn/problems/bLyHh0/">LCR 116. 省份数量</a>
 *
 * @description: 力扣 LCR 116. 省份数量
 * @author: cuiweiman
 * @date: 2023/12/19 10:27
 */
public class LcrNo0116FindCircleNum {
    public static void main(String[] args) {
        LcrNo0116FindCircleNum lcrNo0116FindCircleNum = new LcrNo0116FindCircleNum();
        int[][] isConnected2 = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        int circleNum2 = lcrNo0116FindCircleNum.findCircleNumDfs(isConnected2);
        System.out.println("circleNum2 = " + circleNum2);

        int[][] isConnected = {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}};
        int circleNum = lcrNo0116FindCircleNum.findCircleNumDfs(isConnected);
        System.out.println("findCircleNumDfs = " + circleNum);

        int depthFirstTraversal = lcrNo0116FindCircleNum.depthFirstTraversal(isConnected);
        System.out.println("depthFirstTraversal = " + depthFirstTraversal);

        int breadthFirstSearch = lcrNo0116FindCircleNum.breadthFirstSearch(isConnected);
        System.out.println("breadthFirstSearch = " + breadthFirstSearch);
    }

    /**
     * 深度优先遍历
     * 1. 先确定一个节点，及其所有相连接的节点、间接连接的节点
     * 2. 在确定下一个节点，直到全部访问一遍
     */
    public int findCircleNumDfs(int[][] isConnected) {
        // 没有访问过的节点 标记为 false，访问过的标记为 true
        boolean[] visited = new boolean[isConnected.length];
        int count = 0;
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited[i]) {
                dfs(isConnected, visited, i);
                // 深度遍历结束后，统计一个省份
                count++;
            }
        }
        return count;
    }

    private void dfs(int[][] isConnected, boolean[] visited, int city) {
        if (visited[city]) {
            return;
        }
        // 更新标记
        visited[city] = true;
        for (int i = 0; i < isConnected[city].length; i++) {
            if (!visited[i] && isConnected[city][i] == 1) {
                dfs(isConnected, visited, i);
            }
        }
    }


    public int depthFirstTraversal(int[][] isConnected) {
        // 统计省份数量
        int count = 0;
        // 只需要 一个 一维数组 记录 每个节点是否被访问
        boolean[] visited = new boolean[isConnected.length];
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited[i]) {
                depthFirst(isConnected, visited, i);
                count++;
            }
        }
        return count;
    }

    public void depthFirst(int[][] isConnected, boolean[] visited, int city) {
        if (visited[city]) {
            return;
        }
        visited[city] = true;
        for (int i = 0; i < isConnected[city].length; i++) {
            if (!visited[i] && isConnected[city][i] == 1) {
                depthFirst(isConnected, visited, i);
            }
        }
    }

    /**
     * 广度优先
     * 类似深度优先，把 递归 转换成 队列调用
     */
    public int breadthFirstSearch(int[][] isConnected) {
        int count = 0;
        boolean[] visited = new boolean[isConnected.length];
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited[i]) {
                deque.addLast(i);
                while (!deque.isEmpty()) {
                    Integer removed = deque.removeFirst();
                    visited[removed] = true;
                    for (int j = 0; j < isConnected[i].length; j++) {
                        if (!visited[j] && isConnected[i][j] == 1) {
                            deque.addLast(j);
                        }
                    }
                }
                count++;
            }
        }
        return count;
    }
}
