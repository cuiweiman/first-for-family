package com.first.family.huawei;

import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/longest-substring-without-repeating-characters/">3. 无重复字符的最长子串</a>
 *
 * @description:
 * @author: cuiweiman
 * @date: 2024/2/18 17:32
 */
public class No0003LengthOfLongestSubstring {
    public static void main(String[] args) {
        String str = "abcabcbb";
        // String str = "bbbbb";
        // String str = "pwwkew";
        // String str = " ";
        // String str = "abba";
        No0003LengthOfLongestSubstring demo = new No0003LengthOfLongestSubstring();
        int result = demo.lengthOfLongestSubstring(str);
        System.out.println(result);
        int result2 = demo.lengthOfLongestSubstring2(str);
        System.out.println(result2);
    }

    public int lengthOfLongestSubstring(String s) {
        if (Objects.isNull(s) || s.isBlank()) {
            return 0;
        }
        int len = 1;
        int slow = 0;
        int fast = 1;
        while (slow < fast && fast < s.length()) {
            String sub = s.substring(slow, fast);
            if (sub.contains(s.substring(fast, fast + 1))) {
                slow++;
            } else {
                fast++;
            }
            len = Math.max(len, sub.length());
        }
        return len;
    }


    public int lengthOfLongestSubstring2(String s) {
        if (Objects.isNull(s) || s.isBlank()) {
            return 0;
        }
        int len = 1;
        int slow = 0;
        int fast = 1;
        while (slow <= fast && fast < s.length()) {
            String sub = s.substring(slow, fast);
            if (sub.contains(s.substring(fast, fast + 1))) {
                slow++;
            } else {
                fast++;
                // len = Math.max(len, sub.length() + 1);
            }
            len = Math.max(len, fast - slow);
        }
        return len;
    }
}
