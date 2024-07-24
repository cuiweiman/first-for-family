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
        // String str = "-42";
        // String str = "  -4193 with words";
        // String str = "words and 987";
        // String str = "-91283472332";
        // String str = "+1";
        // String str = "9223372036854775808";
        // String str = "-9223372036854775809";
        // String str = "18446744073709551617";
        // String str = "-2147483647";
        String str = "-91283472332";
        No0008MyAtoi demo = new No0008MyAtoi();
        // int myAtoi = demo.myAtoi(str);
        int myAtoi = demo.myAtoi(str);
        System.out.println(myAtoi);
    }

    public int myAtoi(String s) {
        s = s.trim();
        if (s.isBlank()) {
            return 0;
        }
        System.out.println(Integer.MAX_VALUE + " " + Integer.MIN_VALUE);
        // System.out.println((int) '+' + " " + (int) '-' + " " + (int) '0' + " " + (int) '9');
        char[] charArray = s.toCharArray();
        int result = 0;
        boolean fu = false;
        int begin = 0;
        if (charArray[0] == 43) {
            begin = 1;
        } else if (charArray[0] == 45) {
            fu = true;
            begin = 1;
        }
        for (int i = begin; i < charArray.length; i++) {
            long temp;
            if (charArray[i] < 48 || charArray[i] > 57) {
                break;
            }
            temp = result * 10L + charArray[i] - 48L;
            if (fu && -temp <= Integer.MIN_VALUE) {
                result = Integer.MIN_VALUE;
                break;
            } else if (temp >= Integer.MAX_VALUE) {
                result = Integer.MAX_VALUE;
                break;
            } else {
                result = (int) temp;
            }
        }
        if (fu && result != Integer.MIN_VALUE) {
            result = -result;
        }
        return result;
    }


    public int myAtoiLcr(String str) {
        /*s ="-91283472332"
        输出 -1089159116
        预期结果 -2147483648*/

        str = str.trim();
        if (str.isEmpty()) {
            return 0;
        }
        int result = 0;
        boolean fuShu = false;

        char[] charArray = str.toCharArray();
        int begin = 0;
        if (charArray[0] == 43) {
            begin = 1;
        } else if (charArray[0] == 45) {
            fuShu = true;
            begin = 1;
        }

        for (int i = begin; i < charArray.length; i++) {
            int num = charArray[i];
            if (num >= 48 && num <= 57) {
                if (fuShu) {
                    long temp = -((-result) * 10L + num - 48L);
                    if (temp <= Integer.MIN_VALUE) {
                        result = Integer.MIN_VALUE;
                        break;
                    } else {
                        result = (int) temp;
                    }
                } else {
                    long temp = result * 10L + num - 48L;
                    if (temp >= Integer.MAX_VALUE) {
                        result = Integer.MAX_VALUE;
                        break;
                    } else {
                        result = (int) temp;
                    }
                }
            } else {
                break;
            }
        }
        return result;
    }

}
