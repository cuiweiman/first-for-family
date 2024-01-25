package com.first.family.algorithm;

import com.first.family.algorithm.common.TreeNode;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * <a href="https://leetcode.cn/problems/minimum-depth-of-binary-tree/">力扣 111 二叉树最小深度</a>
 *
 * @description: 力扣 111 二叉树最小深度
 * @author: cuiweiman
 * @date: 2023/12/14 15:28
 */
public class No0111MinimumDepthOfBinaryTree {
    public static void main(String[] args) {
        No0111MinimumDepthOfBinaryTree demo = new No0111MinimumDepthOfBinaryTree();
        TreeNode root = TreeNode.baseTreeNode();
        // TreeNode root = TreeNode.getSymmetric();
        int maxDepth = demo.maxDepth(root);
        int minDepth = demo.minDepth(root);
        int minDepth2 = demo.minDepth2(root);
        System.out.printf("%n maxDepth = %d, minDepth = %d, minDepth2 = %d%n%n", maxDepth, minDepth, minDepth2);
    }

    public int maxDepth(TreeNode root) {
        int depth = 0;
        if (Objects.isNull(root)) {
            return depth;
        }
        LinkedBlockingDeque<TreeNode> queue = new LinkedBlockingDeque<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            depth++;
            for (int i = 0; i < size; i++) {
                TreeNode first = queue.removeFirst();
                if (Objects.nonNull(first.left)) {
                    queue.addLast(first.left);
                }
                if (Objects.nonNull(first.right)) {
                    queue.addLast(first.right);
                }
            }
        }
        return depth;
    }

    /**
     * 深度优先遍历
     * 递归
     */
    public int minDepth2(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        if (Objects.isNull(root.left) && Objects.isNull(root.right)) {
            return 1;
        }
        int min = Integer.MAX_VALUE;
        if (Objects.nonNull(root.left)) {
            min = Math.min(minDepth2(root.left), min);
        }
        if (Objects.nonNull(root.right)) {
            min = Math.min(minDepth2(root.right), min);
        }
        return min + 1;
    }

    /**
     * 广度优先遍历
     * 层序遍历
     */
    public int minDepth(TreeNode root) {
        int depth = 0;
        if (Objects.isNull(root)) {
            return depth;
        }
        LinkedBlockingDeque<TreeNode> queue = new LinkedBlockingDeque<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            depth++;
            for (int i = 0; i < size; i++) {
                TreeNode first = queue.removeFirst();
                if (Objects.isNull(first.left) && Objects.isNull(first.right)) {
                    // 层序遍历，第一个 子节点为空的 肯定是 最小深度，不必再向下遍历了。
                    return depth;
                }
                if (Objects.nonNull(first.left)) {
                    queue.addLast(first.left);
                }
                if (Objects.nonNull(first.right)) {
                    queue.addLast(first.right);
                }
            }
        }
        return depth;
    }

}
