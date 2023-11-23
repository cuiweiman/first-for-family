package com.first.family.algorithm;

import com.first.family.algorithm.common.ListNode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.cn/problems/intersection-of-two-linked-lists/
 *
 * @description: 160. 相交链表
 * @author: cuiweiman
 * @date: 2023/9/14 18:12
 */
public class No0160IntersectionOfTwoLinkedLists {
    public static void main(String[] args) {
        int intersectVal = 2;
        int[] listA = {1, 9, 1, 2, 4};
        int[] listB = {3, 2, 4};
        int skipA = 3;
        int skipB = 1;
        Map<String, ListNode> map = ListNode.createIntersection(intersectVal, listA, listB, skipA, skipB);
        ListNode listNodeA = map.get("listNodeA");
        ListNode listNodeB = map.get("listNodeB");

        No0160IntersectionOfTwoLinkedLists demo = new No0160IntersectionOfTwoLinkedLists();
        ListNode intersectionNode = demo.getIntersectionNode(listNodeA, listNodeB);
        System.out.println(intersectionNode.val);
        System.out.println(intersectionNode.hashCode());
        ListNode intersectionNode2 = demo.getIntersectionNode2(listNodeA, listNodeB);
        System.out.println(intersectionNode2.val);
        System.out.println(intersectionNode2.hashCode());
    }

    /**
     * 若A、B链表相交，则 A 链表遍历完后遍历 B 链表，B 链表完后遍历 A 链表，两次遍历中必定存在一个节点相同
     *
     * @param headA A 链
     * @param headB B 链
     * @return 相交节点
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode ptrA = headA;
        ListNode ptrB = headB;
        while (ptrA != ptrB) {
            ptrA = ptrA == null ? headB : ptrA.next;
            ptrB = ptrB == null ? headA : ptrB.next;
        }
        return ptrA;
    }

    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }
        while (headB != null) {
            if (set.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }
}
