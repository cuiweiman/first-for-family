package com.first.family.meituan;

import com.first.family.algorithm.common.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 2024-02-27 19:30
 * <a href="https://leetcode.cn/problems/LGjMqU/">LCR 026. 重排链表</a>
 *
 * @description: LCR 026. 重排链表
 * @author: cuiweiman
 * @date: 2024/2/28 11:15
 */
public class LrcNo0026ReorderList {
    public static void main(String[] args) {
        // 1,4,2,3
        int[] array = {1, 2, 3, 4};
        ListNode temp = ListNode.createListNode(array);
        LrcNo0026ReorderList demo = new LrcNo0026ReorderList();
        demo.reorderList(temp);
        System.out.println(temp);
    }

    public void reorderList(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        if (list.isEmpty()) {
            return;
        }
        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            list.get(left).next = list.get(right);
            left++;
            if (left == right) {
                break;
            }
            list.get(right).next = list.get(left);
            right--;
        }
        list.get(left).next = null;
    }


}
