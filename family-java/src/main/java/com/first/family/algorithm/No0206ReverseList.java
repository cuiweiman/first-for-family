package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

import java.util.Objects;

/**
 * 206. 反转链表
 *
 * @description: https://leetcode.cn/problems/reverse-linked-list/
 * @author: cuiweiman
 * @date: 2022/11/18 11:20
 */
public class No0206ReverseList {
    public static void main(String[] args) {
        No0206ReverseList demo = new No0206ReverseList();
        int[] array = {1, 2, 3, 4, 5};
        ListNode node = ListNode.createListNode(array);
        System.out.printf("原始节点: %s\n", node);
        ListNode res = demo.reverseList(node);
        System.out.printf("反转结果: %s\n", res);
    }

    /**
     * null  1->2->3->4->5
     * prev  c  n
     * <p>
     * null<-1 2->3->4->5
     * prev  c n
     * .     p c  n
     */
    public ListNode reverseList(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;
        while (Objects.nonNull(curr)) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}

