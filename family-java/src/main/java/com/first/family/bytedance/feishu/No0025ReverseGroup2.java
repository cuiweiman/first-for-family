package com.first.family.bytedance.feishu;

import com.first.family.algorithm.common.ListNode;

import java.util.Objects;

/**
 * K个一组反转变形
 *
 * @description:
 * @author: cuiweiman
 * @date: 2024/3/11 13:25
 */
public class No0025ReverseGroup2 {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8};
        int k = 3;
        No0025ReverseGroup2 demo = new No0025ReverseGroup2();
        ListNode node = ListNode.createListNode(array);
        System.out.println("原始节点: " + node);
        ListNode res = demo.reverseKGroup(node, k);
        System.out.println("K个一组反转: " + res);
        ListNode res2 = demo.reverseKGroup2(ListNode.createListNode(array), k);
        System.out.println("剩余不足也K个反转: " + res2);
        ListNode res3 = demo.reverseKGroup3(ListNode.createListNode(array), k);
        System.out.println("从尾部开始K个一组反转: " + res3);
        ListNode res4 = demo.reverseKGroup4(ListNode.createListNode(array), 3, 6);
        System.out.println("反转指定区域的链表: " + res4);
    }

    /**
     * 反转 left 到 right 位置的元素
     */
    private ListNode reverseKGroup4(ListNode listNode, int begin, int stop) {
        ListNode result = new ListNode();
        result.next = listNode;
        ListNode left = result;
        ListNode right = result;

        for (int i = 0; i < stop; i++) {
            if (i < begin - 1) {
                // 保持 left.next 指向反转的起始节点
                left = left.next;
            }
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


    /**
     * 变形2，从链表尾部开始 k 个一组反转
     */
    private ListNode reverseKGroup3(ListNode listNode, int k) {
        int count = 0;
        ListNode countNode = listNode;
        while (Objects.nonNull(countNode)) {
            countNode = countNode.next;
            count++;
        }

        ListNode result = new ListNode(0);
        result.next = listNode;
        ListNode left = result;
        ListNode right = result;

        int beginIndex = count % k;
        for (int i = 0; i < beginIndex; i++) {
            left = left.next;
            right = right.next;
        }

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

    /**
     * 变形1，剩余元素不足K个也需要反转
     */
    private ListNode reverseKGroup2(ListNode listNode, int k) {
        ListNode result = new ListNode(0);
        result.next = listNode;
        ListNode left = result;
        ListNode right = result;
        ListNode preRight = right;

        while (true) {
            for (int i = 0; i < k && Objects.nonNull(right); i++) {
                preRight = right;
                right = right.next;
            }
            if (Objects.isNull(right)) {
                // 处理剩余部分的反转
                ListNode curr = left.next;
                left.next = curr.next;
                curr.next = preRight.next;
                preRight.next = curr;
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

    /**
     * 原题，K个一组反转
     */
    private ListNode reverseKGroup(ListNode listNode, int k) {
        ListNode result = new ListNode(0);
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
}
