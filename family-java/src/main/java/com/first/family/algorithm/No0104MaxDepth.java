package com.first.family.algorithm;

import com.first.family.algorithm.common.TreeNode;

import java.util.LinkedList;
import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/maximum-depth-of-binary-tree/description/">104. 二叉树的最大深度</a>
 *
 * @description: 104. 二叉树的最大深度
 * @author: cuiweiman
 * @date: 2023/10/19 10:47
 */
public class No0104MaxDepth {
    public static void main(String[] args) {
        TreeNode root = TreeNode.getSymmetric();
        No0104MaxDepth demo = new No0104MaxDepth();
        int maxDepth = demo.maxDepth(root);
        System.out.println(maxDepth);
        int maxDepth2 = demo.maxDepth2(root);
        System.out.println(maxDepth2);
    }

    public int maxDepth(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public int maxDepth2(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        int result = 0;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            result++;
            // 层序遍历
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode remove = queue.removeFirst();
                if (Objects.nonNull(remove.left)) {
                    queue.addLast(remove.left);
                }
                if (Objects.nonNull(remove.right)) {
                    queue.addLast(remove.right);
                }
            }
        }
        return result;
    }


}
