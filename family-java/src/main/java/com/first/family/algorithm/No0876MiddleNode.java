package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

/**
 * <a href="https://leetcode.cn/problems/middle-of-the-linked-list/">链表的中间结点</a>
 *
 * @description: 876 链表中间节点
 * @author: cuiweiman
 * @date: 2023/10/12 10:24
 */
public class No0876MiddleNode {
    public static void main(String[] args) {
        No0876MiddleNode demo = new No0876MiddleNode();
        int[] arr = {1, 2, 3, 4};
        ListNode listNode = ListNode.createListNode(arr);
        System.out.println(demo.middleNode(listNode));
    }

    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null) {
            if (fast.next.next == null) {
                return slow.next;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
