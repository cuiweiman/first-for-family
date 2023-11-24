package com.first.family.algorithm;

/**
 * <a href="https://leetcode.cn/problems/add-strings/description/">415. 字符串相加</a>
 *
 * @description: 415. 字符串相加
 * @author: cuiweiman
 * @date: 2023/11/23 19:27
 */
public class No0415AddStrings {
    public static void main(String[] args) {
        String num1 = "456";
        String num2 = "23";
        No0415AddStrings demo = new No0415AddStrings();
        String addStrings = demo.addStrings(num1, num2);
        System.out.println(addStrings);
    }

    public String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int ptr1 = num1.length();
        int ptr2 = num2.length();
        while (ptr1 != 0 || ptr2 != 0 || carry != 0) {
            // 两个字符串没有遍历完，或者遍历完了，还有进位
            int x = ptr1 == 0 ? 0 : num1.charAt(ptr1 - 1) - '0';
            int y = ptr2 == 0 ? 0 : num2.charAt(ptr2 - 1) - '0';
            int sum = x + y + carry;
            carry = sum / 10;
            result.append(sum % 10);
            // int num = sum % 10;
            // result.insert(0, num);
            if (ptr1 != 0) {
                ptr1--;
            }
            if (ptr2 != 0) {
                ptr2--;
            }
        }
        return result.reverse().toString();
    }
}
