package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/reverse-linked-list-ii/">LeetCode 92 翻转链表 (m,n) 区间的节点</a>
 *
 * @description: LeetCode 92 翻转链表 (m,n) 区间的节点
 * @author: cuiweiman
 * @date: 2023/10/18 09:43
 */
public class No0092ReverseBetween {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int left = 2;
        int right = 4;
        ListNode root = ListNode.createListNode(arr);
        No0092ReverseBetween demo = new No0092ReverseBetween();
        ListNode listNode = demo.reverseBetween(root, left, right);
        System.out.println(listNode);

        ListNode root2 = ListNode.createListNode(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        ListNode listNode2 = demo.reverseBetweenNice(root2, 1, 3);
        System.out.println(listNode2);
    }

    public ListNode reverseBetweenNice(ListNode head, int left, int right) {
        ListNode result = new ListNode();
        result.next = head;
        ListNode start = result, stop = result;
        // 确定需要反转的位置，使得 start.next ==> left, stop ==> right
        for (int i = 0; i < right; i++) {
            if (i + 1 < left) {
                start = start.next;
            }
            stop = stop.next;
        }
        // System.out.println(start + "\n" + stop);
        while (start.next != stop) {
            ListNode current = start.next;
            start.next = current.next;
            current.next = stop.next;
            stop.next = current;
        }
        return result.next;
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }
        ListNode result = new ListNode();
        result.next = head;

        ListNode pre = result;
        for (int i = 0; i < left - 1; i++) {
            // 在 left 之前停下
            pre = pre.next;
        }

        ListNode rightNode = pre;
        for (int i = 0; i < right - left + 1; i++) {
            // 在 right 位置停下
            rightNode = rightNode.next;
        }

        // 待翻转子链表的起始位置
        ListNode leftNode = pre.next;
        // 区间左侧的节点是 pre，还需知道区间右侧的节点，右侧节点翻转后将作为头结点
        ListNode curr = rightNode.next;
        // 截一段子链表出来，将 right 后的链表截断
        rightNode.next = null;
        ListNode temp = null;
        // 暂存子链表的头不变，保持 头节点不变
        ListNode leftNodeTemp = leftNode;
        while (Objects.nonNull(leftNodeTemp)) {
            ListNode next = leftNodeTemp.next;
            leftNodeTemp.next = temp;
            temp = leftNodeTemp;
            leftNodeTemp = next;
        }

        // 子链表翻转结束，拼接左右连结点
        pre.next = temp;
        leftNode.next = curr;
        return result.next;
    }

}
