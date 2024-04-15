package com.first.family.bytedance.feishu;

import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/longest-common-prefix/">14. 最长公共前缀</a>
 * <p>
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * 示例 1：
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * <p>
 * 示例 2：
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 *
 * @description: 14. 最长公共前缀
 * @author: cuiweiman
 * @date: 2024/3/5 16:02
 */
public class No0014LongestCommonPrefix {

    public static void main(String[] args) {
        String[] strs = {"flower", "flow", "flight"};
        // String[] strs = {"dog", "racecar", "car"};
        No0014LongestCommonPrefix demo = new No0014LongestCommonPrefix();
        String result = demo.longestCommonPrefix(strs);
        System.out.println(result);
        String result2 = demo.longestCommonPrefix2(strs);
        System.out.println(result2);
    }


    public String longestCommonPrefix2(String[] strs) {
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            int j = 0;
            for (; j < strs[i].length(); j++) {
                if (prefix.charAt(j) != strs[i].charAt(j)) {
                    break;
                }
            }
            prefix = prefix.substring(0, j);
            if (prefix.isEmpty()) {
                return "";
            }
        }
        return prefix;
    }


    public String longestCommonPrefix(String[] strs) {
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            prefix = compare(prefix, strs[i]);
            if (Objects.equals(prefix, "")) {
                return prefix;
            }
        }
        return prefix;
    }

    public String compare(String prefix, String str) {
        StringBuilder result = new StringBuilder();
        if (prefix.length() > str.length()) {
            String temp = prefix;
            prefix = str;
            str = temp;
        }
        int i = 0;
        while (i < prefix.length() && prefix.substring(i, i + 1).equals(str.substring(i, i + 1))) {
            result.append(prefix, i, i + 1);
            i++;
        }
        return result.toString();
    }


}





