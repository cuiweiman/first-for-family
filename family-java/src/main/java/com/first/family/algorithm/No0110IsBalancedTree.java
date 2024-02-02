package com.first.family.algorithm;

import com.first.family.algorithm.common.TreeNode;

import java.util.LinkedList;
import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/balanced-binary-tree/description/">110. 平衡二叉树</a>
 *
 * @description: 110. 平衡二叉树: 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
 * @author: cuiweiman
 * @date: 2023/10/22 20:09
 */
public class No0110IsBalancedTree {

    public static void main(String[] args) {
        TreeNode root = TreeNode.getSymmetric();
        No0110IsBalancedTree demo = new No0110IsBalancedTree();
        boolean isBalanced = demo.isBalanced(root);
        System.out.println(isBalanced);
        boolean isBalanced2 = demo.isBalanced2(root);
        System.out.println(isBalanced2);
    }

    public boolean isBalanced(TreeNode root) {
        if (Objects.isNull(root)) {
            return true;
        }
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            TreeNode left = pop.left;
            TreeNode right = pop.right;
            int abs = Math.abs(getMaxDepth(left) - getMaxDepth(right));
            if (abs > 1) {
                return false;
            }

            if (Objects.nonNull(left)) {
                stack.push(left);
            }
            if (Objects.nonNull(right)) {
                stack.push(right);
            }

        }
        return true;
    }

    public int getMaxDepth(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        return Math.max(getMaxDepth(root.left), getMaxDepth(root.right)) + 1;
    }

    public boolean isBalanced2(TreeNode root) {
        if (Objects.isNull(root)) {
            return true;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        int abs = Math.abs(getMaxDepth2(left) - getMaxDepth2(right));
        boolean isBalance = isBalanced2(left) && isBalanced2(right);
        return abs <= 1 && isBalance;
    }

    public int getMaxDepth2(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        int result = 0;
        LinkedList<TreeNode> list = new LinkedList<>();
        list.addLast(root);
        while (!list.isEmpty()) {
            result++;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                TreeNode remove = list.removeFirst();
                TreeNode left = remove.left;
                if (Objects.nonNull(left)) {
                    list.addLast(left);
                }
                TreeNode right = remove.right;
                if (Objects.nonNull(right)) {
                    list.addLast(right);
                }
            }
        }
        return result;
    }
}
