package com.first.family.algorithm;

import com.first.family.algorithm.common.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/binary-tree-postorder-traversal/">145. 二叉树的后序遍历</a>
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
 * @description: 145. 二叉树的后序遍历
 * @author: cuiweiman
 * @date: 2023/10/14 19:15
 * @see No0144PreorderTraversal 先序遍历/前序遍历
 * @see No0094InorderTraversal 中序遍历
 * @see No0145PostOrderTraversal 后序遍历
 * @see No0429LevelOrder 层序遍历
 */
public class No0145PostOrderTraversal {

    public static void main(String[] args) {
        TreeNode root = TreeNode.baseTreeNode();

        No0145PostOrderTraversal demo = new No0145PostOrderTraversal();
        List<Integer> list = demo.postorderTraversal(root);
        System.out.println(list);
        List<Integer> list2 = demo.postorderTraversal2(root);
        System.out.println(list2);
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (Objects.nonNull(root)) {
            // 后序遍历 左右中
            ArrayDeque<TreeNode> deque = new ArrayDeque<>();
            deque.push(root);
            while (!deque.isEmpty()) {
                TreeNode pop = deque.pop();
                // 结果集中倒序存放
                result.add(0, pop.val);
                // 左节点比右节点先压栈
                if (Objects.nonNull(pop.left)) {
                    deque.push(pop.left);
                }
                // 右节点后压栈，先处理，出栈时，从 0 下标开始存放到结果集中
                if (Objects.nonNull(pop.right)) {
                    deque.push(pop.right);
                }
            }
        }
        return result;
    }


    public List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        this.doPostorderTraversal2(root, result);
        return result;
    }

    public void doPostorderTraversal2(TreeNode root, List<Integer> result) {
        // 后序遍历左右中
        if (Objects.nonNull(root)) {
            this.doPostorderTraversal2(root.left, result);
            this.doPostorderTraversal2(root.right, result);
            result.add(root.val);
        }
    }
}
