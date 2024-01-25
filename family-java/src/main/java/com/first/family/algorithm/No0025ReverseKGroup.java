package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/reverse-nodes-in-k-group/">25. K 个一组翻转链表</a>
 *
 * @description: 力扣 25. K 个一组翻转链表
 * @author: cuiweiman
 * @date: 2022/11/20 19:33
 */
public class No0025ReverseKGroup {

    public static void main(String[] args) {
        No0025ReverseKGroup demo = new No0025ReverseKGroup();
        // int[] array = {1, 2, 3, 4, 5};
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k = 3;
        ListNode node = ListNode.createListNode(array);
        System.out.println("原始: " + node);
        ListNode res = demo.reverseKGroup(node, k);
        System.out.println("结果: " + res);
        ListNode res2 = demo.reverseKGroup2(ListNode.createListNode(array), k);
        System.out.println("结果: " + res2);
    }

    public ListNode reverseKGroup2(ListNode listNode, int k) {
        // LR->1,2,3,4,5
        ListNode result = new ListNode();
        result.next = listNode;
        ListNode left = result;
        ListNode right = result;

        while (true) {
            for (int i = 0; i < k && right != null; i++) {
                right = right.next;
            }
            if (Objects.isNull(right)) {
                break;
            }
            ListNode leftPtr = left.next;
            while (left.next != right) {
                // 获取当前需要转移指向的指针
                ListNode curr = left.next;
                // left 记录下一个指针, 切断 curr 节点的被指向
                left.next = curr.next;
                // 要将当前节点塞入 right 和 right.next 之间,先修改 curr 的指向,还需要修改 right 的指向
                curr.next = right.next;
                // 修改 right 的指向，完成 curr 节点塞入 right和right.next之间的过程。此时right指针不变，left指针指向下一个节点
                // LR->1,2,3,4,5 k=3
                // r->3,L.next->2,1->4,3->1 :: 2,3,1,4,5 :: L.next->2,r->3
                right.next = curr;
            }
            left = leftPtr;
            right = leftPtr;
        }
        return result.next;
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode result = new ListNode();
        result.next = head;
        ListNode left = result;
        ListNode right = result;
        while (true) {
            for (int i = 0; i < k && right != null; i++) {
                right = right.next;
            }
            if (Objects.isNull(right)) {
                break;
            }
            ListNode leftPtr = left.next;
            while (left.next != right) {
                // 将 current 节点 移动到 right 和 right.next 之间
                ListNode current = left.next;
                left.next = current.next;
                current.next = right.next;
                right.next = current;
            }
            left = leftPtr;
            right = leftPtr;
        }
        return result.next;
    }

}
