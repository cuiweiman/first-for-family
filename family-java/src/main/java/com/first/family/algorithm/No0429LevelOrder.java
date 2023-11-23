package com.first.family.algorithm;

import com.first.family.algorithm.common.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * <a href="https://leetcode.cn/problems/n-ary-tree-level-order-traversal/">429. N 叉树的层序遍历</a>
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
 * @description: 429. N 叉树的层序遍历
 * @author: cuiweiman
 * @date: 2023/10/14 19:19
 * @see No0144PreorderTraversal 先序遍历/前序遍历
 * @see No0094InorderTraversal 中序遍历
 * @see No0145PostOrderTraversal 后序遍历
 * @see No0429LevelOrder 层序遍历
 */
public class No0429LevelOrder {

    public static void main(String[] args) {
        Node node7 = new Node(7);
        List<Node> node5Children = new ArrayList<>();
        node5Children.add(node7);
        Node node5 = new Node(5, node5Children);
        Node node4 = new Node(4);

        List<Node> node2Children = new ArrayList<>();
        node2Children.add(node4);
        node2Children.add(node5);
        Node node2 = new Node(2, node2Children);

        Node node6 = new Node(6);
        List<Node> node3Children = new ArrayList<>();
        node3Children.add(node6);
        Node node3 = new Node(3, node3Children);

        List<Node> nodeRootChildren = new ArrayList<>();
        nodeRootChildren.add(node2);
        nodeRootChildren.add(node3);
        Node root = new Node(1, nodeRootChildren);

        No0429LevelOrder demo = new No0429LevelOrder();
        List<List<Integer>> list = demo.levelOrder(root);
        System.out.println(list);
    }

    public List<List<Integer>> levelOrder(Node root) {
        // 层序遍历不需要压栈、出栈操作，而是自顶向下从左到右一次处理。应使用 Queue
        List<List<Integer>> result = new ArrayList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Node remove = queue.remove();
                level.add(remove.val);
                if (Objects.nonNull(remove.children) && !remove.children.isEmpty()) {
                    queue.addAll(remove.children);
                }
            }
            result.add(level);
        }
        return result;
    }

}
