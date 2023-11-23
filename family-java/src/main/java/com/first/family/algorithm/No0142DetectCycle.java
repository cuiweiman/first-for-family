package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

import java.util.Objects;

/**
 * https://leetcode.cn/problems/linked-list-cycle-ii/
 * 因刚做过题目 141，借鉴一下
 *
 * @description: 142. 环形链表 II
 * @author: cuiweiman
 * @date: 2023/9/11 11:18
 */
public class No0142DetectCycle {
    public static void main(String[] args) {
        No0142DetectCycle demo = new No0142DetectCycle();
        int[] array = {3, 2, 0, -4};
        int pos = 1;
        ListNode cycle = ListNode.createCycle(array, pos);
        ListNode listNode = demo.detectCycle(cycle);
        System.out.println(listNode.val);
    }

    public ListNode detectCycle(ListNode head) {
        if (Objects.isNull(head)) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (Objects.nonNull(fast)) {
            slow = slow.next;
            if (Objects.nonNull(fast.next)) {
                fast = fast.next.next;
            } else {
                return null;
            }
            if (Objects.equals(slow, fast)) {
                ListNode ptr = head;
                while (!Objects.equals(ptr, slow)) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }


    public ListNode detectCycle2(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }

            if (fast == slow) {
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }
}
