package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

/**
 * No 234 回文链表
 *
 * @description: <a href="https://leetcode.cn/problems/palindrome-linked-list/">234 回文链表</a>
 * @author: cuiweiman
 * @date: 2023/10/12 09:50
 */
public class No0234IsPalindrome {

    public static void main(String[] args) {
        // int[] arr = {1, 2, 3, 4, 5, 6, 7};
        // int[] arr = {1, 2, 3, 4, 3, 2, 1};
        int[] arr = {1};
        ListNode listNode = ListNode.createListNode(arr);

        No0234IsPalindrome demo = new No0234IsPalindrome();
        boolean palindrome = demo.isPalindrome(listNode);
        System.out.println(palindrome);
    }

    public boolean isPalindrome(ListNode head) {
        // 先找到中间节点
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode middle = slow.next;
        // 从中间节点开始向后，反转链表
        ListNode prev = null;
        ListNode curr = middle;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        // 从原始链表头节点开始，和反转后的节点进行比较
        while (prev != null) {
            if (head.val != prev.val) {
                return false;
            }
            head = head.next;
            prev = prev.next;
        }
        return true;
    }

}
