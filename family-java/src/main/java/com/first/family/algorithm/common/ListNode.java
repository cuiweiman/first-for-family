package com.first.family.algorithm.common;

import java.util.Objects;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2022/11/18 11:22
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode createListNode(int[] array) {
        ListNode head = new ListNode(array[0]);
        ListNode node = head;
        for (int i = 1; i < array.length; i++) {
            ListNode next = new ListNode(array[i]);
            node.next = next;
            node = next;
        }
        return head;
    }

    @Override
    public String toString() {
        StringBuilder sBuf = new StringBuilder();
        sBuf.append(this.val);
        ListNode node = this.next;
        while (Objects.nonNull(node)) {
            sBuf.append(", ").append(node.val);
            node = node.next;
        }
        return sBuf.toString();
    }

}
