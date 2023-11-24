package com.first.family.algorithm;

/**
 * 在 content 字符串中，找到首个完全匹配 find 字符串的子串，并返回该子串在 content 中的下标
 *
 * @description: 字符串匹配算法 / 字符串搜索算法
 * @author: cuiweiman
 * @date: 2023/11/24 10:25
 */
public class StringSearchAlgorithm {

    public static void main(String[] args) {
        // String content = "sdJpsdf";
        // String content = "ssdfsdf";
        String content = "ssdasdb";
        // String content = "ssssdsdsdsdsdfsdf";
        String pattern = "sdf";

        StringSearchAlgorithm demo = new StringSearchAlgorithm();
        Integer bfFind = demo.bfFind(content, pattern);
        System.out.println(bfFind);
    }

    /**
     * 暴力破解
     * 最好时间复杂度 O(n)，最坏时间复杂度 O(m*n)
     */
    public Integer bfFind(String content, String pattern) {
        int ptr1 = 0;
        int ptr2 = 0;
        while (ptr1 < content.length() - pattern.length()) {
            // 剩余长度不足，则肯定 无匹配子串
            if (content.charAt(ptr1) == pattern.charAt(ptr2)) {
                ptr1++;
                ptr2++;
            } else {
                // 回朔：ptr1 指针回到原始位置，但是要向右移动一位，对比新的序列
                ptr1 = ptr1 - ptr2 + 1;
                ptr2 = 0;
            }
            if (ptr2 == pattern.length()) {
                // 返回 子串 在 字符串中的其实位置
                // return ptr1 - pattern.length();
                return ptr1 - ptr2;
            }
        }
        return -1;
    }

}
