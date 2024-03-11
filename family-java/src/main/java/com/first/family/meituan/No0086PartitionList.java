package com.first.family.meituan;

import com.first.family.algorithm.common.ListNode;

import java.util.Objects;

/**
 * 2024-02-27 19:30
 * <a href="https://leetcode.cn/problems/partition-list/">86. 分隔链表</a>
 *
 * @description: 86. 分隔链表
 * @author: cuiweiman
 * @date: 2024/2/28 10:08
 */
public class No0086PartitionList {
    public static void main(String[] args) {
        int[] array = {1, 4, 3, 2, 5, 2};
        int x = 3;
        /*int[] array = {2, 3};
        int x = 2;*/
        ListNode test = ListNode.createListNode(array);
        No0086PartitionList demo = new No0086PartitionList();
        ListNode partition = demo.partition(test, x);
        System.out.println(partition);

    }

    public ListNode partition2(ListNode head, int x) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }
        // 两个链表，一存小于，一个存大于，再连接成一个链表
        ListNode small = new ListNode(0);
        ListNode smallHead = small;
        ListNode large = new ListNode(0);
        ListNode largeHead = large;
        while (head != null) {
            if (head.val < x) {
                small.next = head;
                small = small.next;
            } else {
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }
        large.next = null;
        small.next = largeHead.next;
        return smallHead.next;
    }

    /**
     * 尾插法，大于x的插入链表尾，但结果存储需要额外注意
     */
    public ListNode partition(ListNode head, int x) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }

        int count = 0;
        ListNode last = head;
        while (Objects.nonNull(last.next)) {
            last = last.next;
            count++;
        }

        ListNode result = new ListNode(0);
        result.next = head;
        ListNode prePtr = result;
        while (Objects.nonNull(prePtr.next) && count >= 0) {
            if (prePtr.next.val < x) {
                prePtr = prePtr.next;
            } else {
                ListNode curr = prePtr.next;
                prePtr.next = curr.next;
                curr.next=null;
                last.next = curr;
                last = last.next;
            }
            count--;
        }

        return result.next;
    }
}
