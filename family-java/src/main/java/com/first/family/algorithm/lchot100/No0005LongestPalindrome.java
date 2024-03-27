package com.first.family.algorithm.lchot100;

/**
 * <a href="https://leetcode.cn/problems/longest-palindromic-substring/description/?envType=study-plan-v2&envId=top-100-liked">5. 最长回文子串</a>
 * <p>
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
 * <p>
 * 示例 1：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * <p>
 * 示例 2：
 * 输入：s = "cbbd"
 * 输出："bb"
 * <p>
 * 提示：
 * 1 <= s.length <= 1000
 * s 仅由数字和英文字母组成
 * <p>
 * 动态规划 abcdcb dp(i,j) =  dp(i+1,j-1) : Si==Sj 时
 * *    0a  1b  2c  3d  4c  5b
 * 0a   1   0   0   0   0   0
 * 1b       1   0   0   0   1
 * 2c           1   0   1   0
 * 3d               1   0   0
 * 4c                   1   0
 * 5b                       1
 * <p>
 * 第0行-> 0,0 1,1 2,2 3,3 4,4 5,5
 * 第1行-> 0,1 1,2 2,3 3,4 4,5
 * 第2行-> 0,2 1,3 2,4 3,5
 * <p>
 * 再从 dp 数组中，定位 i 与 j 间隔最大的两个下标即可。
 *
 * @description: 5. 最长回文子串
 * @author: cuiweiman
 * @date: 2024/3/26 22:14
 */
public class No0005LongestPalindrome {

    public static void main(String[] args) {
        String s = "babad";
        // String s = "dabcdcbae";
        // String s = "cdbd";
        // String s = "abcdcb";
        No0005LongestPalindrome demo = new No0005LongestPalindrome();
        String syy = demo.syy(s);
        System.out.println("syy = " + syy);
        String res = demo.longestPalindrome(s);
        System.out.println("res = " + res);
        String res2 = demo.longestPalindrome2(s);
        System.out.println("res2 = " + res2);
    }

    public String longestPalindrome2(String s) {
        String res = "";
        int len = s.length();
        int rows = 0;
        while (rows < len) {
            for (int i = 0; i < len - rows; i++) {
                int j = i + rows;
                if (s.charAt(i) == s.charAt(j) && res.length() < s.substring(i, j + 1).length()) {
                    res = s.substring(i, j + 1);
                }
            }
            rows++;
        }
        return res;
    }


    public String longestPalindrome(String s) {
        int temp = 0;
        String res = "";
        int len = s.length();
        // int[][] dp = new int[len][len];
        // 行数
        int rows = 0;
        // 行数 小于 数组长度
        while (rows < len) {
            // 下标 i < len - rows, 下标 j = i + rows
            for (int i = 0; i < len - rows; i++) {
                int j = i + rows;
                if (s.charAt(i) == s.charAt(j)) {
                    // dp[i][j] = 1;
                    if (temp < j - i) {
                        temp = j - i;
                        // substring(begin,end) 左闭右开
                        res = s.substring(i, j + 1);
                    }
                }
            }
            // 下一行
            rows++;
        }
        return res;
    }

    public String syy(String s) {
        int len = s.length();
        int[][] dp = new int[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j + i < len; j++) {
                if (j == j + i) { //长度为1
                    dp[j][j + i] = 1;
                } else if (j == j + i - 1 && s.charAt(j) == s.charAt(j + i)) {//长度为2且相等
                    dp[j][j + i] = 1;
                } else if (s.charAt(j) == s.charAt(j + i)) {
                    dp[j][j + i] = dp[j + 1][j + i - 1];
                }
            }
        }
        int temp = 0;
        int begin = 0;
        int end = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (dp[i][j] == 1) {
                    if (j - i > temp) {
                        begin = i;
                        end = j;
                        temp = j - i;
                    }
                }
            }
        }
        System.out.println(temp);
        return s.substring(begin, end + 1);
    }
}
