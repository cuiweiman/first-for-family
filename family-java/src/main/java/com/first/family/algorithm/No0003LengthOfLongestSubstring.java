package com.first.family.algorithm;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 3. 无重复字符的最长子串
 *
 * @description: https://leetcode.cn/problems/longest-substring-without-repeating-characters/
 * @author: cuiweiman
 * @date: 2022/11/17 10:44
 */
public class No0003LengthOfLongestSubstring {

    public static void main(String[] args) {
        // String str = "abcabcbb";
        String str = "bbbbb";
        // String str = "pwwkew";
        // String str = " ";
        // String str = "abba";
        No0003LengthOfLongestSubstring algorithm = new No0003LengthOfLongestSubstring();
        int result = algorithm.lengthOfLongestSubstring(str);
        System.out.println(result);
        int result2 = algorithm.lengthOfLongestSubstring2(str);
        System.out.println(result2);
        int result3 = algorithm.lengthOfLongestSubstring3(str);
        System.out.println(result3);
        int result4 = algorithm.lengthOfLongestSubstring4(str);
        System.out.println(result4);

    }

    public int lengthOfLongestSubstring4(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        int len = 1;
        int left = 0;
        int right = 1;
        while (left < right && right < s.length()) {
            String sub = s.substring(left, right);
            if (sub.contains(s.substring(right, right + 1))) {
                left++;
                right = left + 1;
            } else {
                right++;
                len = Math.max(len, sub.length() + 1);
            }
        }
        return len;
    }

    /**
     * 双指针
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        int len = 1;
        int head = 0;
        int foot = 1;
        while (head <= foot && foot < s.length()) {
            String substring = s.substring(head, foot);
            if (substring.contains(s.substring(foot, foot + 1))) {
                head++;
            } else {
                foot++;
                len = Math.max(len, substring.length() + 1);
            }
        }
        return len;
    }

    /**
     * 滑动窗口思想
     */
    public int lengthOfLongestSubstring2(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int head = 0, foot = 0, len = 1;
        HashMap<Character, Integer> map = new HashMap<>();
        while (head <= foot && foot < s.length()) {
            if (map.containsKey(chars[foot])) {
                // 左侧重复的坐标 向右移动 一位，使得坐标队列中的数据不重复。取最大值，避免获取到 旧的、重复元素、的坐标
                head = Math.max(map.get(chars[foot]) + 1, head);
            }
            // 将新的坐标，添加 或者 覆盖(重复时)到 map 中，并向右滑动一步
            map.put(chars[foot], foot);
            len = Math.max(len, foot - head + 1);
            foot++;
        }
        return len;
    }

    /**
     * 分析以上两个方法，主要思想都是 滑动窗口，主要存储内容 是 『记录重复元素出现的坐标』。
     * 主要滑动的坐标是 左指针
     * 由于 String 由 char 构成，而 char 与 int 类型根据 ASCII 息息相关。因此……
     */
    public int lengthOfLongestSubstring3(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        int len = 1, head = -1;
        int[] record = new int[128];
        Arrays.fill(record, -1);
        for (int i = 0; i < s.length(); i++) {
            char index = s.charAt(i);
            // 若 head 值 更大(此时实际上是 head==record[index]=-1 )，表示当前元素不重复，head 位置不变。
            // 若 record[index] 值更大， 表示字符串中 (char)index 重复了，此时 head 要右移一个下标
            head = Math.max(head, record[index] + 1);
            // 滑动窗口的 长度 = 当前位置 - 左指针 + 1
            len = Math.max(len, i - head + 1);
            // 记录当前元素出现的下标
            record[index] = i;
        }
        return len;
    }

}
