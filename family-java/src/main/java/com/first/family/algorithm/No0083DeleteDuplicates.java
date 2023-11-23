package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

/**
 * 删除排序链表中的重复元素
 *
 * @description: <a href="https://leetcode.cn/problems/merge-two-sorted-lists/'>leetcode 83 删除排序链表中的重复元素</a>
 * @author: cuiweiman
 * @date: 2023/9/1 11:00
 */
public class No0083DeleteDuplicates {

    public static void main(String[] args) {
        int[] array = {1, 1, 2, 3, 3};
        ListNode listNode = ListNode.createListNode(array);
        No0083DeleteDuplicates demo = new No0083DeleteDuplicates();
        ListNode deleteDuplicates = demo.deleteDuplicates(listNode);
        System.out.println(deleteDuplicates);
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        while (current != null && current.next != null) {
            if (current.val == current.next.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        return head;
    }

}
