package com.first.family.review;

import com.first.family.algorithm.common.ListNode;

import java.util.Objects;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2024/1/24 11:51
 */
public class 链表合并 {
    public static void main(String[] args) {
        链表合并 demo = new 链表合并();
        int[] arr1 = {1, 2, 4};
        int[] arr2 = {1, 3, 4};

        ListNode listNodeRes = demo.mergeTwoLists(ListNode.createListNode(arr1), ListNode.createListNode(arr2));
        System.out.println(listNodeRes.toString());
        ListNode listNodeRes2 = demo.mergeTwoLists2(ListNode.createListNode(arr1), ListNode.createListNode(arr2));
        System.out.println(listNodeRes2.toString());
    }

    private ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        ListNode curr = new ListNode();
        ListNode res = curr;
        while (Objects.nonNull(list1) && Objects.nonNull(list2)) {
            if (list1.val < list2.val) {
                curr.next = list1;
                list1 = list1.next;
            } else {
                curr.next = list2;
                list2 = list2.next;
            }
            curr = curr.next;
        }
        if (Objects.nonNull(list1)) {
            curr.next = list1;
        }

        if (Objects.nonNull(list2)) {
            curr.next = list2;
        }
        return res.next;
    }

    private ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (Objects.isNull(list1)) {
            return list2;
        }
        if (Objects.isNull(list2)) {
            return list1;
        }
        if (list1.val < list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }
}
