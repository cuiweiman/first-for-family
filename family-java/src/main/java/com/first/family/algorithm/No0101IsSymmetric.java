package com.first.family.algorithm;

import com.first.family.algorithm.common.TreeNode;

import java.util.LinkedList;
import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/symmetric-tree/">101 对称二叉树</a>
 *
 * @description: 力扣 101 对称二叉树
 * @author: cuiweiman
 * @date: 2023/10/19 09:16
 */
public class No0101IsSymmetric {

    public static void main(String[] args) {
        TreeNode root = TreeNode.getSymmetric();
        No0101IsSymmetric demo = new No0101IsSymmetric();
        boolean symmetric = demo.isSymmetric(root);
        System.out.println(symmetric);
        boolean symmetric2 = demo.isSymmetric2(root);
        System.out.println(symmetric2);
    }

    public boolean isSymmetric(TreeNode root) {
        boolean isEmpty = Objects.isNull(root) || (Objects.isNull(root.left) && Objects.isNull(root.right));
        if (isEmpty) {
            return true;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);
        while (!queue.isEmpty()) {
            TreeNode nodeA = queue.remove();
            TreeNode nodeB = queue.remove();

            if (Objects.isNull(nodeA) && Objects.isNull(nodeB)) {
                continue;
            }
            if (Objects.isNull(nodeA) || Objects.isNull(nodeB)) {
                return false;
            }
            if (nodeA.val != nodeB.val) {
                return false;
            }
            queue.add(nodeA.left);
            queue.add(nodeB.right);

            queue.add(nodeA.right);
            queue.add(nodeB.left);
        }
        return true;
    }


    public boolean isSymmetric2(TreeNode root) {
        // 递归
        if (Objects.isNull(root)) {
            return true;
        }
        return dfs(root.left, root.right);
    }

    public boolean dfs(TreeNode left, TreeNode right) {
        if (Objects.isNull(left) && Objects.isNull(right)) {
            // 两个都不为空
            return true;
        }
        if (Objects.isNull(left) || Objects.isNull(right)) {
            // 其中一个为空
            return false;
        }
        if (left.val != right.val) {
            return false;
        }
        // left 和 right 相等，还需要继续递归判断
        return dfs(left.left, right.right) && dfs(left.right, right.left);
    }
}
