package com.first.family.huawei;

import com.first.family.algorithm.common.MyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 经典的「拓扑排序」问题： 若有环（有向无环图），则不是拓扑排序； 有向无环图，则拓扑排序结果可能不止一种。
 * 这里只需要一种排序结果就可以了。
 * <p>
 * 现在你总共有 numCourses 门课需要选，记为 0 到 numCourses - 1。
 * 给你一个数组 prerequisites ，其中 prerequisites[i] = [ai, bi] ，表示在选修课程 ai 前 必须 先选修 bi 。
 * 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示：[0,1] 。
 * 返回你为了学完所有课程所安排的学习顺序。可能会有多个正确的顺序，你只要返回 任意一种 就可以了。如果不可能完成所有课程，返回 一个空数组
 * <p>
 * 示例 1：
 * 输入：numCourses = 2, prerequisites = [[1,0]]
 * 输出：[0,1]
 * 解释：总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
 * <p>
 * 示例 2：
 * 输入：numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
 * 输出：[0,2,1,3]
 * 解释：总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
 * 因此，一个正确的课程顺序是 [0,1,2,3] 。另一个正确的排序是 [0,2,1,3] 。
 * <p>
 * 示例 3：
 * 输入：numCourses = 1, prerequisites = []
 * 输出：[0]
 * <p>
 * 提示：
 * 1 <= numCourses <= 2000
 * 0 <= prerequisites.length <= numCourses * (numCourses - 1)
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * ai != bi
 * 所有[ai, bi] 互不相同
 * <p>
 * tips:
 * 深度优先遍历 or 广度优先遍历
 *
 * @description: <a href="https://leetcode.cn/problems/course-schedule-ii/description/">210. 课程表 II</a>
 * @author: cuiweiman
 * @date: 2024/2/2 11:28
 */
public class No0210CourseSchedule2 {
    public static void main(String[] args) {
        No0210CourseSchedule2 demo = new No0210CourseSchedule2();
        int numCourses = 4;
        int[][] prerequisites = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        /*int numCourses = 2;
        int[][] prerequisites = {{1, 0}};*/
        int[] order = demo.findOrder(numCourses, prerequisites);
        MyUtil.intArrPrint(order);
    }

    int index;
    // 有向无环图可以拓扑排序，有向有环图非法。true —— 合法 & 无环。
    boolean valid = true;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] result = new int[numCourses];
        index = result.length - 1;
        // 0-未搜索,1-搜索中,2-搜索完成
        int[] visited = new int[numCourses];
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < numCourses; i++) {
            edges.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        for (int i = 0; i < prerequisites.length && valid; i++) {
            // 开始遍历有向图
            if (visited[i] == 0) {
                dfs(edges, visited, i, result);
            }
        }
        if (!valid) {
            return new int[0];
        }
        return result;
    }


    private void dfs(List<List<Integer>> edges, int[] visited, int i, int[] result) {
        visited[i] = 1;
        for (Integer integer : edges.get(i)) {
            if (visited[integer] == 0) {
                dfs(edges, visited, integer, result);
                if (!valid) {
                    return;
                }
            } else if (visited[integer] == 1) {
                // 拓扑有环
                valid = false;
                return;
            }
        }
        visited[i] = 2;
        result[index--] = i;
    }

    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        // 使用数组模拟栈，存储最终结果。通过下标参数递减，模拟入栈操作
        int[] result = new int[numCourses];
        index = result.length - 1;
        // 标记每一个节点的遍历过程中，0-未搜索；1-搜索中；2-搜索完成。也用于判断是否存在环。
        int[] visited = new int[numCourses];
        // 存储和并构造 有向图
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            edges.get(prerequisite[1]).add(prerequisite[0]);
        }
        // 开始遍历图
        for (int i = 0; i < numCourses && valid; i++) {
            if (visited[i] == 0) {
                dfs2(edges, visited, i, result);
            }
        }
        if (!valid) {
            return new int[0];
        }
        return result;
    }

    private void dfs2(List<List<Integer>> edges, int[] visited, int i, int[] result) {
        // 将节点标记为搜索中
        visited[i] = 1;
        for (Integer integer : edges.get(i)) {
            if (visited[integer] == 0) {
                dfs2(edges, visited, integer, result);
                if (!valid) {
                    return;
                }
            } else if (visited[integer] == 1) {
                // 有环：非法
                valid = false;
                return;
            }
        }
        // 遍历结束，节点状态修改为 搜索完成
        visited[i] = 2;
        result[index--] = i;
    }

}
