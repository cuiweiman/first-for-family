package com.first.family.algorithm;

import com.first.family.algorithm.common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/binary-tree-inorder-traversal/description/">94. 二叉树的中序遍历</a>
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
 * 前：1,2,4,5,7,3,6
 * 中：4,2,7,5,1,6,3
 * 后：4,7,5,2,6,3,1
 * 层：1,2,3,4,5,6,7
 *
 * @description: 94. 二叉树的中序遍历 左中右
 * @author: cuiweiman
 * @date: 2023/10/14 17:24
 * @see No0144PreorderTraversal 前序遍历
 * @see No0094InorderTraversal 中序遍历
 * @see No0145PostOrderTraversal 后序遍历
 * @see No0429LevelOrder 层序遍历
 */
public class No0094InorderTraversal {
    public static void main(String[] args) {
        TreeNode root = TreeNode.baseTreeNode();

        No0094InorderTraversal demo = new No0094InorderTraversal();
        List<Integer> list = demo.inorderTraversal(root);
        System.out.println(list);
        List<Integer> list2 = demo.inorderTraversal2(root);
        System.out.println(list2);
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        Deque<TreeNode> deque = new ArrayDeque<>();
        while (Objects.nonNull(root) || !deque.isEmpty()) {
            if (Objects.nonNull(root)) {
                // 先处理 最左子树 全部节点，入栈
                deque.push(root);
                root = root.left;
            } else {
                // root 为空，但是 栈 不为空
                // 最左子树处理结束，开始逐个出栈，每出栈一个左子树节点，都要处理一次其右子树节点
                root = deque.pop();
                result.add(root.val);
                root = root.right;
            }
        }
        return result;
    }

    public List<Integer> inorderTraversal2(TreeNode root) {
        // 递归
        List<Integer> result = new ArrayList<>();
        doInorderTraversal2(root, result);
        return result;
    }

    public void doInorderTraversal2(TreeNode root, List<Integer> result) {
        if (Objects.nonNull(root)) {
            doInorderTraversal2(root.left, result);
            result.add(root.val);
            doInorderTraversal2(root.right, result);
        }
    }
}
