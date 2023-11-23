package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

/**
 * <a href="https://leetcode.cn/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/">链表倒数 k 个节点</a>
 *
 * @description: LCR 140. 训练计划 II 链表倒数 k 个节点
 * @author: cuiweiman
 * @date: 2023/10/12 22:27
 */
public class NoLcr0140TrainingPlan {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        int cnt = 2;
        ListNode listNode = ListNode.createListNode(arr);
        NoLcr0140TrainingPlan demo = new NoLcr0140TrainingPlan();
        ListNode node = demo.trainingPlan(listNode, cnt);
        System.out.println(node);
    }

    public ListNode trainingPlan(ListNode head, int cnt) {
        ListNode slow = head;
        ListNode fast = head;
        for (int i = 0; i < cnt; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
