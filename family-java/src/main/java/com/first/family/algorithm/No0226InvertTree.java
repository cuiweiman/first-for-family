package com.first.family.algorithm;

import com.first.family.algorithm.common.TreeNode;

import java.util.LinkedList;
import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/invert-binary-tree/">226 反转二叉树</a>
 *
 * <pre class="code">
 *        1
 *     /     \
 *    2      3
 *   / \    /
 *  4   5  6
 *     /
 *    7
 * </pre>
 * 原始树的层次遍历：1234567
 * 反转树的层次遍历：1326547
 *
 * @description: 226 反转二叉树
 * @author: cuiweiman
 * @date: 2023/10/23 10:05
 */
public class No0226InvertTree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.baseTreeNode();
        No0226InvertTree demo = new No0226InvertTree();
        // 反转
        TreeNode invertTree = demo.invertTree(root);
        System.out.println(invertTree);
        // 再反转，则恢复原结构
        TreeNode invertTree2 = demo.invertTree2(root);
        System.out.println(invertTree2);
    }

    public TreeNode invertTree(TreeNode root) {
        if (Objects.isNull(root)) {
            return null;
        }
        TreeNode node = root;
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || Objects.nonNull(node)) {
            if (Objects.nonNull(node)) {
                stack.push(node);
                node = node.left;
            } else {
                TreeNode pop = stack.pop();
                TreeNode left = pop.left;
                TreeNode right = pop.right;
                pop.left = right;
                pop.right = left;
                node = right;
            }
        }
        return root;
    }

    public TreeNode invertTree2(TreeNode root) {
        if (Objects.isNull(root)) {
            return root;
        }
        invertTree2(root.left);
        invertTree2(root.right);
        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;
        return root;
    }
}
