package com.first.family.weizhong;

import com.first.family.algorithm.StringSearchAlgorithm;

/**
 * 字符串搜索
 *
 * @description: 判断A是B的子串
 * @author: cuiweiman
 * @date: 2024/3/13 20:17
 * @see StringSearchAlgorithm
 */
public class Test1 {
    public static void main(String[] args) {
        String a = "zxcvbn";
        String b = "qwertzxcvbnm";
        Boolean result = contains(a, b);
        System.out.println(result);
    }

    private static Boolean contains(String a, String b) {
        int ptrA = 0;
        int slow = 0;
        int fast = 0;
        while (fast < b.length()) {
            if (a.charAt(ptrA) == b.charAt(fast)) {
                ptrA++;
                fast++;
            } else {
                ptrA = 0;
                slow++;
                fast = slow;
            }
            if (ptrA == a.length()) {
                System.out.println("位置: " + slow);
                return true;
            }
        }
        return false;
    }

}
