package com.first.family.huawei;

import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/longest-word-in-dictionary-through-deleting/">524. 通过删除字母匹配到字典里最长单词</a>
 * <p>
 * 给你一个字符串 s 和一个字符串数组 dictionary ，找出并返回 dictionary 中最长的字符串，该字符串可以通过删除 s 中的某些字符得到。
 * 如果答案不止一个，返回长度最长且字母序最小的字符串。如果答案不存在，则返回空字符串。
 * <p>
 * 示例 1：
 * 输入：s = "abpcplea", dictionary = ["ale","apple","monkey","plea"]
 * 输出："apple"
 * <p>
 * 示例 2：
 * 输入：s = "abpcplea", dictionary = ["a","b","c"]
 * 输出："a"
 *
 * @description: 524. 通过删除字母匹配到字典里最长单词
 * @author: cuiweiman
 * @date: 2024/3/6 22:34
 */
public class No0524FindLongestWord {
    public static void main(String[] args) {
        String s = "abpcplea";
        List<String> dictionary = List.of("ale", "apple", "monkey", "plea");

        No0524FindLongestWord demo = new No0524FindLongestWord();
        String longestWord = demo.findLongestWord(s, dictionary);
        System.out.println(longestWord);
    }

    public String findLongestWord(String s, List<String> dictionary) {
        String result = "";
        for (String dictEle : dictionary) {
            int i = 0;
            int j = 0;
            while (i < dictEle.length() && j < s.length()) {
                if (dictEle.charAt(i) == s.charAt(j)) {
                    i++;
                }
                j++;
            }
            boolean flag = i == dictEle.length()
                    && (dictEle.length() > result.length()
                    || dictEle.length() == result.length() && dictEle.compareTo(result) < 0);
            // dictEle.compareTo(result)<0 排序靠前的比较小，因此需要结果小于0
            if (flag) {
                result = dictEle;
            }
        }
        return result;
    }

}
