package com.first.family.review;

import com.first.family.algorithm.common.ListNode;

import java.util.Objects;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/12/6 10:15
 */
public class 链表反转 {

    public static void main(String[] args) {
        链表反转 demo = new 链表反转();
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // int[] arr = {1, 2, 3, 4, 5};
        ListNode listNode = ListNode.createListNode(arr);
        ListNode 反转 = demo.反转(listNode);
        System.out.println(反转);
        ListNode listNod = ListNode.createListNode(new int[]{1, 2, 3, 4, 5, 6});
        ListNode 反转2 = demo.反转2(listNod);
        System.out.println(反转2);

        ListNode listNode2 = ListNode.createListNode(arr);
        int k = 3;
        ListNode k个一组反转 = demo.k个一组反转(listNode2, k);
        System.out.println(k个一组反转);

        ListNode listNode3 = ListNode.createListNode(arr);
        // 1,2,3,6,5,4,7,8,9,10
        ListNode 反转指定节点之间的链表 = demo.反转指定节点之间的链表(listNode3, 4, 6);
        System.out.println(反转指定节点之间的链表);

        int[] cycleArray = {3, 2, 0, -4};
        int cyclePos = 1;
        ListNode cycle = ListNode.createCycle(cycleArray, cyclePos);
        System.out.println("链表是否有环: " + demo.链表是否有环(cycle));

    }

    public ListNode 反转(ListNode listNode) {
        ListNode result = null;
        ListNode curr = listNode;
        while (Objects.nonNull(curr)) {
            ListNode next = curr.next;
            curr.next = result;
            result = curr;
            curr = next;
        }
        return result;
    }

    public ListNode 反转2(ListNode listNode) {
        ListNode result = new ListNode();
        result.next = listNode;
        ListNode left = result;
        ListNode right = result;
        while (Objects.nonNull(right.next)) {
            right = right.next;
        }
        while (left.next != right) {
            ListNode curr = left.next;
            left.next = curr.next;
            curr.next = right.next;
            right.next = curr;
        }
        return result.next;
    }

    public ListNode k个一组反转(ListNode listNode, int k) {
        ListNode result = new ListNode();
        result.next = listNode;
        ListNode left = result;
        ListNode right = result;
        while (true) {
            for (int i = 0; i < k && Objects.nonNull(right); i++) {
                right = right.next;
            }
            if (Objects.isNull(right)) {
                break;
            }
            ListNode leftPtr = left.next;
            while (left.next != right) {
                ListNode curr = left.next;
                left.next = curr.next;
                curr.next = right.next;
                right.next = curr;
            }
            left = leftPtr;
            right = leftPtr;
        }
        return result.next;
    }

    public ListNode 反转指定节点之间的链表(ListNode listNode, int left, int right) {
        ListNode result = new ListNode();
        result.next = listNode;
        ListNode slow = result;
        ListNode fast = result;
        for (int i = 0; i < right; i++) {
            fast = fast.next;
            if (i + 1 < left) {
                slow = slow.next;
            }
        }
        while (slow.next != fast) {
            ListNode curr = slow.next;
            slow.next = curr.next;
            curr.next = fast.next;
            fast.next = curr;
        }
        return result.next;
    }

    public boolean 链表是否有环(ListNode listNode) {
        ListNode slow = listNode, fast = listNode;
        while (Objects.nonNull(fast) && Objects.nonNull(fast.next)) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }


}

