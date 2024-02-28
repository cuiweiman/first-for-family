package com.first.family.review;

import com.first.family.algorithm.common.MyUtil;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println("省份数量: " + circleNum);
        /*int[][] isConnected2 = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        int circleNum2 = demo.findCircleNumDfs(isConnected2);
        System.out.println("circleNum2 = " + circleNum2);*/

        // N.O. 210 课程表选课
        int numCourses = 4;
        int[][] prerequisites = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        /*int numCourses = 2;
        int[][] prerequisites = {{1, 0}};*/
        int[] order = demo.findOrder(numCourses, prerequisites);
        MyUtil.intArrPrint("课程学习顺序: ", order);
    }

    // 拓扑图 true-有向无环合法，false-有向有环非法-无法完成拓扑排序
    boolean valid = true;
    int index;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] result = new int[numCourses];
        index = result.length - 1;
        // 存储节点的访问状态 0-未访问；1-访问中；2-访问完成
        int[] visited = new int[numCourses];

        // 使用二维集合 构造拓扑图
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            edges.get(prerequisite[1]).add(prerequisite[0]);
        }
        // 遍历有向拓扑图
        for (int i = 0; i < edges.size() && valid; i++) {
            if (visited[i] == 0) {
                dfsOrder(edges, i, visited, result);
            }
        }
        if (!valid) {
            return new int[0];
        }
        return result;
    }

    public void dfsOrder(List<List<Integer>> edges, int i, int[] visited, int[] result) {
        visited[i] = 1;
        for (Integer integer : edges.get(i)) {
            if (visited[integer] == 0) {
                dfsOrder(edges, integer, visited, result);
                if (!valid) {
                    return;
                }
            } else if (visited[integer] == 1) {
                valid = false;
                return;
            }
        }
        visited[i] = 2;
        result[index--] = i;
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
            }
        }
    }
}
