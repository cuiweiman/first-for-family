package com.first.family.review;

import com.first.family.algorithm.common.Node;
import com.first.family.algorithm.common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/12/14 13:53
 */
public class 二叉树 {
    public static void main(String[] args) {
        二叉树 demo = new 二叉树();
        TreeNode root = TreeNode.baseTreeNode();

        List<Integer> 前序遍历2 = demo.前序遍历2(root);
        System.out.println("前序遍历2: " + 前序遍历2);
        List<Integer> 前序遍历 = demo.前序遍历(root);
        System.out.println("前序遍历: " + 前序遍历);
        List<Integer> 中序遍历2 = demo.中序遍历2(root);
        System.out.println("中序遍历2: " + 中序遍历2);
        List<Integer> 中序遍历 = demo.中序遍历(root);
        System.out.println("中序遍历: " + 中序遍历);
        List<Integer> 后序遍历2 = demo.后序遍历2(root);
        System.out.println("后序遍历2: " + 后序遍历2);
        List<Integer> 后序遍历 = demo.后序遍历(root);
        System.out.println("后序遍历: " + 后序遍历);
        Node levelNode = Node.levelNode();
        List<List<Integer>> 层序遍历2 = demo.层序遍历2(levelNode);
        System.out.println("层序遍历2: " + 层序遍历2);
        List<List<Integer>> 层序遍历 = demo.层序遍历(levelNode);
        System.out.println("层序遍历: " + 层序遍历);
    }

    public List<Integer> 前序遍历(TreeNode root) {
        // 中左右
        List<Integer> result = new ArrayList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        LinkedBlockingDeque<TreeNode> stack = new LinkedBlockingDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            result.add(pop.val);
            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
        }
        return result;
    }

    public List<Integer> 前序遍历2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        // 中左右, 使用栈
        LinkedBlockingDeque<TreeNode> stack = new LinkedBlockingDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            result.add(pop.val);
            if (Objects.nonNull(pop.right)) {
                stack.push(pop.right);
            }
            if (Objects.nonNull(pop.left)) {
                stack.push(pop.left);
            }
        }
        return result;
    }

    public List<Integer> 中序遍历(TreeNode root) {
        // 左中右
        List<Integer> result = new ArrayList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        LinkedBlockingDeque<TreeNode> stack = new LinkedBlockingDeque<>();
        while (!stack.isEmpty() || Objects.nonNull(root)) {
            if (Objects.nonNull(root)) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                result.add(root.val);
                root = root.right;
            }
        }
        return result;
    }

    public List<Integer> 中序遍历2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        LinkedBlockingDeque<TreeNode> stack = new LinkedBlockingDeque<>();
        while (Objects.nonNull(root) || !stack.isEmpty()) {
            if (Objects.nonNull(root)) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                result.add(root.val);
                root = root.right;
            }
        }
        return result;
    }

    public List<Integer> 后序遍历(TreeNode root) {
        // 左右中，按照 中右左 的顺序遍历，然后倒序存放即可
        List<Integer> result = new ArrayList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        LinkedBlockingDeque<TreeNode> stack = new LinkedBlockingDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            result.add(0, pop.val);
            if (Objects.nonNull(pop.left)) {
                stack.push(pop.left);
            }
            if (Objects.nonNull(pop.right)) {
                stack.push(pop.right);
            }
        }
        return result;
    }

    public List<Integer> 后序遍历2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        // 左右中
        LinkedBlockingDeque<TreeNode> stack = new LinkedBlockingDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            // 倒序存储  中右左
            TreeNode pop = stack.pop();
            result.add(0, pop.val);
            if (Objects.nonNull(pop.left)) {
                stack.push(pop.left);
            }
            if (Objects.nonNull(pop.right)) {
                stack.push(pop.right);
            }
        }
        return result;
    }

    public List<List<Integer>> 层序遍历(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        LinkedBlockingQueue<Node> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Node remove = queue.remove();
                level.add(remove.val);
                if (Objects.nonNull(remove.children)) {
                    queue.addAll(remove.children);
                }
            }
            result.add(level);
        }
        return result;
    }

    public List<List<Integer>> 层序遍历2(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        LinkedBlockingDeque<Node> queue = new LinkedBlockingDeque<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            int loop = queue.size();
            List<Integer> level = new ArrayList<>(loop);
            for (int i = 0; i < loop; i++) {
                Node first = queue.removeFirst();
                level.add(first.val);
                if (Objects.nonNull(first.children)) {
                    queue.addAll(first.children);
                }
            }
            result.add(level);
        }
        return result;
    }

}
