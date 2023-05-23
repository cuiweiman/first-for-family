package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

import java.util.Objects;

/**
 * 25. K 个一组翻转链表
 *
 * @description: https://leetcode.cn/problems/reverse-nodes-in-k-group/
 * @author: cuiweiman
 * @date: 2022/11/20 19:33
 */
public class No0025ReverseKGroup {

    public static void main(String[] args) {
        No0025ReverseKGroup demo = new No0025ReverseKGroup();
        int[] array = {1, 2, 3, 4, 5};
        int k = 2;
        ListNode node = ListNode.createListNode(array);
        System.out.println("原始: " + node);
        ListNode res = demo.reverseKGroup(node, k);
        System.out.println("结果: " + res);
    }

    public ListNode reverseKGroup2(ListNode head, int k) {

        return null;
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        // 链表补充一个 null，作为翻转过程中的的指向
        // result->left->nodeA->nodeB->right->nodeC->nodeD->null
        ListNode result = new ListNode();
        result.next = head;
        ListNode left = result, right = result;
        while (true) {
            // 定位 翻转的 链表距离，left 和 right 之间进行反转
            int count = 0;
            while (Objects.nonNull(right) && count != k) {
                right = right.next;
                count++;
            }
            if (Objects.isNull(right)) {
                // count 小于 k ，但是 链表已经遍历结束了
                break;
            }
            ListNode record = left.next;
            // 循环结束条件:左右指针重合
            while (left.next != right) {
                ListNode curr = left.next;
                left.next = curr.next;
                curr.next = right.next;
                right.next = curr;
            }
            left = record;
            right = record;
        }
        return result.next;
    }

}
