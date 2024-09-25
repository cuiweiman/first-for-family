package com.first.family.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description: 113. 路径总和 II
 * @author: cuiweiman
 * @date: 2024/9/25 20:10
 */
public class No0113PathSum {
    public static void main(String[] args) {
        No0113PathSum demo = new No0113PathSum();
        TreeNode l41 = new TreeNode(7);
        TreeNode l42 = new TreeNode(2);
        TreeNode l43 = new TreeNode(5);
        TreeNode l44 = new TreeNode(1);
        TreeNode l31 = new TreeNode(11);
        l31.left = l41;
        l31.right = l42;
        TreeNode l32 = new TreeNode(13);
        TreeNode l33 = new TreeNode(4);
        l33.left = l43;
        l33.right = l44;
        TreeNode l21 = new TreeNode(4);
        l21.left = l31;
        TreeNode l22 = new TreeNode(8);
        l22.left = l32;
        l22.right = l33;
        TreeNode root = new TreeNode(5);
        root.left = l21;
        root.right = l22;

        List<List<Integer>> lists = demo.pathSum(root, 22);
        System.out.println(lists);
    }

    List<List<Integer>> result = new ArrayList<>();
    List<Integer> temp = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        // 递归
        dfs(root, 0, targetSum);
        return result;
    }

    public void dfs(TreeNode node, int tempSum, int targetNum) {
        if (Objects.isNull(node)) {
            return;
        }
        tempSum += node.val;
        temp.add(node.val);
        if (tempSum == targetNum && Objects.isNull(node.left) && Objects.isNull(node.right)) {
            // 临时赋值，避免 移除节点时 因对象引用导致 result 结果出问题。同时顺序执行，使得 temp 移除末尾元素
            result.add(new ArrayList<>(temp));
        }
        dfs(node.left, tempSum, targetNum);
        dfs(node.right, tempSum, targetNum);
        temp.remove(temp.size() - 1);
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

