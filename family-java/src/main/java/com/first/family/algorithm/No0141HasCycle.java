package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 141. 环形链表 https://leetcode.cn/problems/linked-list-cycle/
 *
 * @description: 判断环形链表
 * @author: cuiweiman
 * @date: 2023/9/10 18:26
 */
public class No0141HasCycle {

    public static void main(String[] args) {
        No0141HasCycle demo = new No0141HasCycle();
        int[] arr = {3, 2, 0, -4};
        int pos = 1;
        ListNode listNode = ListNode.createCycle(arr, pos);
        boolean hasCycle = demo.hasCycle(listNode);
        System.out.println(hasCycle);
        boolean hasCycle2 = demo.hasCycle2(listNode);
        System.out.println(hasCycle2);
    }

    /**
     * 快慢指针，fast 指针移动两个元素，slow 移动一个元素，若两个指针可以相逢，那么有环
     */
    public boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            // fast 移动快，因此只需要判断 fast 即可
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (!set.add(head)) {
                // Set 元素不可重复，若重复则有环
                return true;
            }
            head = head.next;
        }
        return false;
    }
}
