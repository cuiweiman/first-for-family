package com.first.family.algorithm;

import com.first.family.weizhong.Test1;

/**
 * 在 content 字符串中，找到首个完全匹配 find 字符串的子串，并返回该子串在 content 中的下标
 * <p>
 * KMP算法：通过查找 好前缀，移动好前缀个位置，减少匹配次数
 * <p>
 * BM算法 实现过程比较复杂，通过 坏字符 和 好后缀 移动，减少匹配次数。
 *
 * @description: 字符串匹配算法 / 字符串搜索算法
 * @author: cuiweiman
 * @date: 2023/11/24 10:25
 * @see Test1
 */
public class StringSearchAlgorithm {

    public static void main(String[] args) {
        String text = "sdJpsdf";
        // String text = "ssdfsdf";
        // String text = "ssdasdb";
        // String text = "ssssdsdsdsdsdfsdf";
        String pattern = "sdf";

        // String text = "BCADBCABCABCD";
        // String pattern = "BCADBCD";

        StringSearchAlgorithm demo = new StringSearchAlgorithm();
        Integer bfFind = demo.bfFind(text, pattern);
        System.out.println("bfFind = " + bfFind);
        Integer bfFind2 = demo.bfFind2(text, pattern);
        System.out.println("bfFind2 = " + bfFind2);
        Integer bfFind3 = demo.bfFind3(text, pattern);
        System.out.println("bfFind3 = " + bfFind3);

        Integer kmpFind = demo.kmpFind(text, pattern);
        System.out.println("kmpFind = " + kmpFind);
    }

    /**
     * KMP算法
     * 概念变量：
     * 前缀和后缀：若 text = headString + otherString + tailString, 则 headString 是text前缀，tailString 是text的后缀
     * PMT值: 前缀集合 和 后缀集合 的交集中，最长元素的长度
     * 部分匹配表: PMT 值集合，字符串所有前缀的PMT值
     * prefix: 每个下标位置对应一个PMT值，组成的数组
     * next数组: prefix 向右移动一个下标位置，组成 next 数组
     * <p>
     * 概念变量的计算方式：
     * ABCABCD
     * index=0 A     	：没有前缀；		没有后缀；	PMT=0；
     * index=1 AB 		：前缀 {A}		后缀 {B}		PMT=0 前后缀没有交集；
     * index=2 ABC      ：前缀 {A,AB} 	后缀 {C,BC}	PMT=0；
     * index=3 ABCA 	：前缀 {A,AB,ABC}	后缀 {A,CA,BCA} 	PMT=1 前后缀交集是A；
     * index=4 ABCAB 	：前缀 {A,AB,ABC,ABCA}	后缀：{B,AB,CAB,BCAB} 	PMT=2 交集是AB；
     * <p>
     * 思路：
     * 寻找交集 PMT 值的目的：好前缀；KMP是根据 好前缀的长度 向右 移动的，据此减少匹配次数。
     * 若 前缀和后缀有交集，则表示前缀和后缀可以匹配，那么在移动模式串时，可以直接移动到交集的位置。
     *
     * @param text    全量串 ABCABCAABCABCD
     * @param pattern 模式串 ABCDBC
     * @return 模式串匹配位置
     */
    public Integer kmpFind(String text, String pattern) {
        int[] kmpNext = kmpNext(pattern.toCharArray());
        // System.out.println(Arrays.toString(kmpNext));
        int i = 0;
        int j = 0;
        while (i < text.length() && j < pattern.length()) {
            if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                // 从 kmpNext[j] 的位置开始继续比较，之前的前缀作为好前缀，不需要再次比较
                j = kmpNext[j];
            }
        }
        if (j == pattern.length()) {
            return i - j;
        }
        return -1;
    }


    /**
     * 比较模式串
     * BCADBCD
     * *BCADBCD
     */
    public int[] kmpNext(char[] pattern) {
        int[] next = new int[pattern.length];
        next[0] = -1;
        int i = 0;
        int j = -1;
        while (i < pattern.length) {
            if (j == -1) {
                i++;
                j++;
            } else if (pattern[i] == pattern[j]) {
                i++;
                j++;
                // 模式串的前缀和后缀匹配，则 第i个位置和第j个位置一样，比较完第i个位置后直接比较第j个位置
                next[i] = j;
            } else {
                // 回溯
                j = next[j];
            }
        }
        return next;
    }

    /**
     * 暴力破解一
     * 最好时间复杂度 O(n)，最坏时间复杂度 O(m*n)
     */
    public Integer bfFind(String text, String pattern) {
        if (text.length() == 0 || pattern.length() == 0 || text.length() < pattern.length()) {
            return -1;
        }
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            String sub = text.substring(i, i + pattern.length());
            if (sub.equals(pattern)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 暴力破解二
     */
    public Integer bfFind2(String text, String pattern) {
        int ptr1 = 0;
        int ptr2 = 0;
        while (ptr2 < text.length() - pattern.length()) {
            // 剩余长度不足，则肯定 无匹配子串
            if (text.charAt(ptr1) == pattern.charAt(ptr2)) {
                ptr1++;
                ptr2++;
            } else {
                // 回溯：ptr1 指针回到原始位置，但是要向右移动一位，对比新的序列
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


    /**
     * 暴力破解三
     */
    private static Integer bfFind3(String text, String pattern) {
        int ptrA = 0;
        int slow = 0;
        int fast = 0;
        while (fast < text.length()) {
            if (pattern.charAt(ptrA) == text.charAt(fast)) {
                ptrA++;
                fast++;
            } else {
                // 回溯
                ptrA = 0;
                slow++;
                fast = slow;
            }
            if (ptrA == pattern.length()) {
                return slow;
            }
        }
        return -1;
    }

}
