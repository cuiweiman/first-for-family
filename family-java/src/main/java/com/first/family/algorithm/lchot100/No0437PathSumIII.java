package com.first.family.algorithm.lchot100;

import com.first.family.algorithm.common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/path-sum-iii/description/?envType=study-plan-v2&envId=top-100-liked">437. 路径总和 III</a>
 * <p>
 * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
 * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 * <p>
 * 二叉树的题目一般都是用递归或者栈实现，根据题目描述，先判断题目应该使用 栈 还是 递归。
 *
 * @description: 437. 路径总和 III
 * @author: cuiweiman
 * @date: 2024/3/28 22:12
 */
public class No0437PathSumIII {

    public static void main(String[] args) {
        // TreeNode root = TreeNode.pathSumTreeNode();
        // int target = 8;

        TreeNode root = new TreeNode(-2);
        TreeNode node = new TreeNode(-3);
        root.right = node;
        int target = -5;

        No0437PathSumIII demo = new No0437PathSumIII();
        int result = demo.pathSum(root, target);
        System.out.println(result);
        int pathSum2 = demo.pathSum2(root, target);
        System.out.println("pathSum2 = " + pathSum2);
    }

    public int pathSum2(TreeNode root, int targetSum) {
        if (Objects.isNull(root)) {
            return 0;
        }
        int result = dfs2(root, targetSum);
        result += pathSum2(root.left, targetSum);
        result += pathSum2(root.right, targetSum);
        return result;
    }

    public int dfs2(TreeNode root, int targetSum) {
        if (Objects.isNull(root)) {
            return 0;
        }
        int result = 0;
        int value = root.val;
        if (value == targetSum) {
            result++;
        }
        result += dfs2(root.left, targetSum - value);
        result += dfs2(root.right, targetSum - value);
        return result;
    }

    public int pathSum(TreeNode root, int targetSum) {
        if (Objects.isNull(root)) {
            return 0;
        }
        int ret = dfs(root, targetSum);
        ret += pathSum(root.left, targetSum);
        ret += pathSum(root.right, targetSum);
        return ret;
    }

    public int dfs(TreeNode root, long targetSum) {
        int ret = 0;
        if (Objects.isNull(root)) {
            return ret;
        }
        int value = root.val;
        if (value == targetSum) {
            ret++;
        }
        ret += dfs(root.left, targetSum - value);
        ret += dfs(root.right, targetSum - value);
        return ret;
    }

    public void preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        doPreorderTraversal(root, list);
        System.out.println(list);
    }

    public void doPreorderTraversal(TreeNode root, List<Integer> list) {
        if (Objects.isNull(root)) {
            return;
        }
        // 先序遍历
        list.add(root.val);
        doPreorderTraversal(root.left, list);
        // 中序遍历
        // list.add(root.val);
        doPreorderTraversal(root.right, list);
        // 后序遍历
        // list.add(root.val);
    }

}
