package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

/**
 * 合并两个有序链表
 *
 * @description: <a href="https://leetcode.cn/problems/merge-two-sorted-lists/'>leetcode 21 合并两个有序链表</a>
 * @author: cuiweiman
 * @date: 2023/9/1 09:51
 */
public class No0021MergeTwoList {

    public static void main(String[] args) {
        No0021MergeTwoList demo = new No0021MergeTwoList();
        int[] arr1 = {1, 2, 4};
        ListNode listNode1 = ListNode.createListNode(arr1);
        int[] arr2 = {1, 3, 4};
        ListNode listNode2 = ListNode.createListNode(arr2);

        ListNode listNode = demo.mergeTwoLists(listNode1, listNode2);
        System.out.println(listNode.toString());
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val <= list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }

    public ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        ListNode current = new ListNode();
        ListNode result = current;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        while (list1 != null) {
            current.next = list1;
            list1 = list1.next;
            current = current.next;
        }
        while (list2 != null) {
            current.next = list2;
            list2 = list2.next;
            current = current.next;
        }
        return result.next;
    }
}
