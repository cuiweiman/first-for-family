package com.first.family.algorithm.lchot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <a href="https://leetcode.cn/problems/group-anagrams/description/">49. 字母异位词分组</a>
 * <p>
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
 * <p>
 * 示例 1:
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * <p>
 * 示例 2:
 * 输入: strs = [""]
 * 输出: [[""]]
 * <p>
 * 示例 3:
 * 输入: strs = ["a"]
 * 输出: [["a"]]
 * <p>
 * 1 <= strs.length <= 104
 * 0 <= strs[i].length <= 100
 * strs[i] 仅包含小写字母
 *
 * @description: 49. 字母异位词分组
 * @author: cuiweiman
 * @date: 2024/3/20 10:56
 */
public class No0049GroupAnagrams {
    public static void main(String[] args) {
        String[] string = {"eat", "tea", "tan", "ate", "nat", "bat"};

        No0049GroupAnagrams demo = new No0049GroupAnagrams();
        List<List<String>> lists = demo.groupAnagrams(string);
        System.out.println(lists);
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> maps = new HashMap<>();
        for (String str : strs) {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            String key = new String(charArray);
            if (Objects.isNull(maps.get(key))) {
                maps.put(key, new ArrayList<>());
            }
            maps.get(key).add(str);
        }
        List<List<String>> result = new ArrayList<>();
        maps.forEach((k, v) -> result.add(v));
        return result;
    }
}
