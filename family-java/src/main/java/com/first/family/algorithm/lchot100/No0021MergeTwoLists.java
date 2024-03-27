package com.first.family.algorithm.lchot100;

import com.first.family.algorithm.common.ListNode;

/**
 * <a href="https://leetcode.cn/problems/merge-two-sorted-lists/description/?envType=study-plan-v2&envId=top-100-liked">21. 合并两个有序链表</a>
 *
 * @description: 21. 合并两个有序链表
 * @author: cuiweiman
 * @date: 2024/3/27 22:04
 */
public class No0021MergeTwoLists {
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 4};
        int[] arr2 = {1, 3, 4};
        No0021MergeTwoLists demo = new No0021MergeTwoLists();
        ListNode result = demo.mergeTwoLists(ListNode.createListNode(arr1), ListNode.createListNode(arr2));
        System.out.println("cwm = " + result.toString());
        ListNode syy = demo.syy(ListNode.createListNode(arr1), ListNode.createListNode(arr2));
        System.out.println("syy = " + syy.toString());

    }

    private ListNode syy(ListNode list1, ListNode list2) {

        return null;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode result = new ListNode();
        result.next = list1;
        ListNode pre = result;
        while (pre.next != null && list2 != null) {
            if (pre.next.val < list2.val) {
                pre = pre.next;
            } else {
                ListNode temp = list2.next;
                list2.next = pre.next;
                pre.next = list2;

                pre = pre.next;
                list2 = temp;
            }
        }
        if (list2 != null) {
            pre.next = list2;
        }
        return result.next;
    }

}
