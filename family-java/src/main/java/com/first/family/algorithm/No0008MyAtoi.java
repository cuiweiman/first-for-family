package com.first.family.algorithm;

import com.first.family.weizhong.Test1;

/**
 * <a href="https://leetcode.cn/problems/string-to-integer-atoi/description/">8. 字符串转换整数 (atoi)</a>
 * <a href="https://leetcode.cn/problems/ba-zi-fu-chuan-zhuan-huan-cheng-zheng-shu-lcof/submissions/">LCR 192</a>
 *
 * @description: 8. 字符串转换整数 (atoi)
 * @author: cuiweiman
 * @date: 2024/3/22 10:07
 * @see Test1
 */
public class No0008MyAtoi {
    public static void main(String[] args) {
        String str = "-42";
        // String str = "  -4193 with words";
        // String str = "words and 987";
        // String str = "-91283472332";
        // String str = "+1";
        // String str = "9223372036854775808";
        // String str = "-9223372036854775809";
        // String str = "18446744073709551617";
        // String str = "-2147483647";
        // String str = "-91283472332";
        No0008MyAtoi demo = new No0008MyAtoi();
        int myAtoi2 = demo.myAtoi2(str);
        System.out.println(myAtoi2);
        int myAtoiLcr = demo.myAtoiLcr(str);
        System.out.println(myAtoiLcr);
    }

    public int myAtoi2(String s) {
        s = s.trim();
        if (s.isEmpty()) {
            return 0;
        }
        int begin = 0;
        // 是否是 负数
        boolean negative = false;
        char firstChar = s.charAt(0);
        if (firstChar < '0') {
            // 小于 0 的ASCII，那么可能是 + 或 -，判断一下数字开始位置
            if (firstChar == '-') {
                negative = true;
            } else if (firstChar == '+') {
                negative = false;
            } else {
                // 首字符 既不是数字，也不是正负号，无法转换
                return 0;
            }
            // 首字符 是正负号，那么转换的位置 从下一个 开始
            begin++;
        }
        long result = 0;
        while (begin < s.length() && result <= Integer.MAX_VALUE) {
            int digit = Character.digit(s.charAt(begin++), 10);
            result = result * 10 + digit;
        }
        if (result >= Integer.MAX_VALUE) {
            return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        return negative ? -(int) result : (int) result;
    }

    public int myAtoiLcr(String str) {
        str = str.trim();
        if (str.isEmpty()) {
            return 0;
        }
        int begin = 0;
        boolean negative = false;
        char c = str.charAt(0);
        if (c < '0') {
            if (c == '-') {
                negative = true;
            } else if (c != '+') {
                // 收个字符非 正负号，且 ASCII 小于 0
                return 0;
            }
            begin++;
        }
        long result = 0;
        while (begin < str.length() && result <= Integer.MAX_VALUE) {
            int digit = Character.digit(str.charAt(begin++), 10);
            result = result * 10 + digit;
        }
        if (result >= Integer.MAX_VALUE) {
            return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        return negative ? -(int) result : (int) result;
    }

}
