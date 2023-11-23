package com.first.family.algorithm.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description: 链表节点
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

    /**
     * 创建有环链表
     *
     * @param array 数组
     * @param pos   环位置
     * @return res
     */
    public static ListNode createCycle(int[] array, int pos) {
        ListNode head = new ListNode();
        ListNode node = head;
        ListNode cycle = null;
        for (int i = 0; i < array.length; i++) {
            ListNode next = new ListNode(array[i]);
            if (i == pos) {
                cycle = next;
            }
            node.next = next;
            node = next;
        }
        node.next = cycle;
        return head.next;
    }

    /**
     * 创建相交链表
     *
     * @param intersectVal 相交位置
     * @param a            array a
     * @param b            array b
     * @param skipA        array a 相交位置之前的元素个数
     * @param skipB        array b 相交位置之前的元素个数
     */
    public static Map<String, ListNode> createIntersection(int intersectVal, int[] a, int[] b, int skipA, int skipB) {
        Map<String, ListNode> map = new HashMap<>(2);
        ListNode listNodeA;
        ListNode listNodeB;
        if (intersectVal <= 0) {
            listNodeA = createListNode(a);
            listNodeB = createListNode(b);
            map.put("listNodeA", listNodeA);
            map.put("listNodeB", listNodeB);
            return map;
        }
        listNodeA = createListNode(a);
        ListNode ptr = listNodeA;
        for (int i = 0; i < skipA; i++) {
            ptr = ptr.next;
        }

        listNodeB = new ListNode(b[0]);
        ListNode tempB = listNodeB;
        for (int i = 1; i < b.length; i++) {
            if (skipB == i) {
                tempB.next = ptr;
                map.put("listNodeA", listNodeA);
                map.put("listNodeB", listNodeB);
                return map;
            } else {
                ListNode temp = new ListNode(b[i]);
                tempB.next = temp;
            }
        }
        // some error
        map.put("listNodeA", listNodeA);
        map.put("listNodeB", listNodeB);
        return map;
    }

    public static void main(String[] args) {
        int intersectVal = 2;
        int[] listA = {1, 9, 1, 2, 4};
        int[] listB = {3, 2, 4};
        int skipA = 3;
        int skipB = 1;
        Map<String, ListNode> map = createIntersection(intersectVal, listA, listB, skipA, skipB);
        ListNode listNodeA = map.get("listNodeA");
        ListNode listNodeB = map.get("listNodeB");
        System.out.println(listNodeA);
        System.out.println(listNodeB);
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * 使用 HashSet 时涉及到 equals 和 hashCode 的重写
     *
     * @param obj obj
     * @return res
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
