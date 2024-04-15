package com.first.family.algorithm;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/permutation-in-string/description/">567. 字符串的排列</a>
 *
 * @description: 567. 字符串的排列
 * @author: cuiweiman
 * @date: 2024/4/1 23:25
 */
public class No0567CheckInclusion {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "ooolleoooleh";
        No0567CheckInclusion demo = new No0567CheckInclusion();
        boolean checked = demo.checkInclusion(s1, s2);
        System.out.println(checked);
    }

    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        int[] a1 = new int[26];
        int[] a2 = new int[26];
        int s1Len = s1.length();
        for (int i = 0; i < s1Len; i++) {
            a1[s1.charAt(i) - 'a']++;
            a2[s2.charAt(i) - 'a']++;
        }
        if (Arrays.equals(a1, a2)) {
            return true;
        }
        for (int i = s1Len; i < s2.length(); i++) {
            a2[s2.charAt(i) - 'a']++;
            a2[s2.charAt(i - s1Len) - 'a']--;
            if (Arrays.equals(a1, a2)) {
                return true;
            }
        }
        return false;
    }


    public boolean checkInclusion2(String s1, String s2) {
        int i = 0;
        String sortedS1 = sorted(s1);
        while (i < s2.length()) {
            if (s1.contains(s2.substring(i, i + 1))) {
                int j = i + s1.length();
                if (j > s2.length()) {
                    return false;
                }

                String sub = s2.substring(i, j);
                int m = 0;
                for (; m < s1.length(); m++) {
                    if (!sub.contains(s1.substring(m, m + 1))) {
                        break;
                    }
                }
                if (m == s1.length() && sorted(sub).equals(sortedS1)) {
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    public String sorted(String s) {
        char[] charArray = s.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }
}
