package com.first.family.meituan;

import com.first.family.algorithm.common.ListNode;

import java.util.Objects;

/**
 * @description: 25. K 个一组翻转链表
 * @author: cuiweiman
 * @date: 2024/2/23 15:33
 */
public class No0025ReverseGroup {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8};
        int k = 3;
        No0025ReverseGroup demo = new No0025ReverseGroup();
        ListNode node = ListNode.createListNode(array);
        System.out.println("原始: " + node);
        ListNode res = demo.reverseKGroup(node, k);
        System.out.println("结果: " + res);
        ListNode res2 = demo.reverseKGroup2(ListNode.createListNode(array), k);
        System.out.println("剩余节点也翻转: " + res2);
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode result = new ListNode();
        result.next = head;
        ListNode left = result;
        ListNode right = result;
        while (true) {
            for (int i = 0; i < k && Objects.nonNull(right); i++) {
                right = right.next;
            }
            if (Objects.isNull(right)) {
                break;
            }
            ListNode ptr = left.next;
            while (left.next != right) {
                ListNode curr = left.next;
                left.next = curr.next;
                curr.next = right.next;
                right.next = curr;
            }
            left = ptr;
            right = ptr;
        }
        return result.next;
    }

    /**
     * 最后剩余的节点也翻转
     */
    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode result = new ListNode();
        result.next = head;
        ListNode left = result;
        ListNode right = result;
        ListNode preRight = result;
        while (true) {
            for (int i = 0; i < k && Objects.nonNull(right); i++) {
                preRight = right;
                right = right.next;
            }
            if (Objects.isNull(right)) {
                while(left.next!=preRight){
                    ListNode curr = left.next;
                    left.next=curr.next;
                    curr.next=null;
                    preRight.next=curr;
                }
                break;
            }
            ListNode ptr = left.next;
            while (left.next != right) {
                ListNode curr = left.next;
                left.next = curr.next;
                curr.next = right.next;
                right.next = curr;
            }
            left = ptr;
            right = ptr;
        }
        return result.next;
    }
}
