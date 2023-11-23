package com.first.family.algorithm.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @description: 二叉树
 * @author: cuiweiman
 * @date: 2023/10/14 16:23
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }


    /**
     * <pre class="code">
     *        1
     *     /     \
     *    2      3
     *   / \    /
     *  4   5  6
     *     /
     *    7
     * </pre>
     * 前：1,2,4,5,7,3,6
     * 中：4,2,7,5,1,6,3
     * 后：4,7,5,2,6,3,1
     * 层：1,2,3,4,5,6,7
     *
     * @return 常用树构造
     */
    public static TreeNode baseTreeNode() {
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        root.left = node2;
        root.right = node3;
        node2.left = node4;
        node2.right = node5;
        node5.left = node7;
        node3.left = node6;
        return root;
    }

    public static TreeNode getSymmetric() {
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(2);
        TreeNode node4 = new TreeNode(3);
        TreeNode node5 = new TreeNode(4);
        TreeNode node6 = new TreeNode(4);
        TreeNode node7 = new TreeNode(3);
        root.left = node2;
        root.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        return root;
    }

    @Override
    public String toString() {
        List<Integer> list = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(this);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode treeNode = queue.removeFirst();
                list.add(treeNode.val);
                if (Objects.nonNull(treeNode.left)) {
                    queue.addLast(treeNode.left);
                }
                if (Objects.nonNull(treeNode.right)) {
                    queue.addLast(treeNode.right);
                }
            }
        }
        return list.toString();
    }
}
