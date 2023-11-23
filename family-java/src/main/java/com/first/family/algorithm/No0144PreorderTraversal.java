package com.first.family.algorithm;

import com.first.family.algorithm.common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/binary-tree-preorder-traversal/">144. 二叉树的前序遍历</a>
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
 * @description: 144. 二叉树的先序遍历/前序遍历
 * @author: cuiweiman
 * @date: 2023/10/14 19:15
 * @see No0144PreorderTraversal 先序遍历/前序遍历
 * @see No0094InorderTraversal 中序遍历
 * @see No0145PostOrderTraversal 后序遍历
 * @see No0429LevelOrder 层序遍历
 */
public class No0144PreorderTraversal {
    public static void main(String[] args) {
        TreeNode root = TreeNode.baseTreeNode();

        No0144PreorderTraversal demo = new No0144PreorderTraversal();
        List<Integer> list = demo.preorderTraversal(root);
        System.out.println(list);
        List<Integer> list2 = demo.preorderTraversal2(root);
        System.out.println(list2);
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        // 栈 迭代
        List<Integer> result = new ArrayList<>();
        if (Objects.nonNull(root)) {
            Deque<TreeNode> deque = new ArrayDeque<>();
            // 先序遍历中左右，根节点先入栈，先入栈先处理
            deque.push(root);
            while (!deque.isEmpty()) {
                root = deque.pop();
                result.add(root.val);
                // 先入栈的后处理，按照 左中右原则，应该先入栈右节点，再入栈左节点
                if (Objects.nonNull(root.right)) {
                    deque.push(root.right);
                }
                if (Objects.nonNull(root.left)) {
                    deque.push(root.left);
                }
            }
        }
        return result;
    }


    public List<Integer> preorderTraversal2(TreeNode root) {
        // 递归
        List<Integer> result = new ArrayList<>();
        this.doPreorderTraversal2(root, result);
        return result;
    }

    public void doPreorderTraversal2(TreeNode root, List<Integer> result) {
        if (Objects.nonNull(root)) {
            // 先序遍历 中左右，因此先处理中、在处理左、最后处理右
            result.add(root.val);
            doPreorderTraversal2(root.left, result);
            doPreorderTraversal2(root.right, result);
        }
    }

}
