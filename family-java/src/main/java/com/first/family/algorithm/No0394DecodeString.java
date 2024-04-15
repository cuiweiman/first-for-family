package com.first.family.algorithm;

import java.util.Collections;
import java.util.LinkedList;

/**
 * <a href="https://leetcode.cn/problems/decode-string/">394. 字符串解码</a>
 *
 * @description: 394. 字符串解码
 * @author: cuiweiman
 * @date: 2023/10/13 00:06
 */
public class No0394DecodeString {

    public static void main(String[] args) {
        No0394DecodeString demo = new No0394DecodeString();
        String test = "3[a2[c]]";
        // String test = "3[a]2[bc]";
        // String test = "2[abc]3[cd]ef";
        // String test = "abc3[cd]xyz";
        // String test = "3[z]2[2[y]pq4[2[jk]e1[f]]]ef";
        String s = demo.decodeString(test);
        System.out.println(s);
    }


    /**
     * 1. 字符串逐个字符入栈，直到 ']'，开始处理出栈字符的拼接;
     * 2. 字符出栈，直到读取到了 '['，然后拼接获取 [] 中间的字符串，再出栈一步，得到 中间字符串重复的次数;
     * 3. 第二步拼接好之后，将字符串入栈，继续遍历原始字符串
     * 4. 最终结果，是栈中的字符串，根据出栈规则，需要出栈并倒序拼接出结果。
     */

    public String decodeString(String s) {
        LinkedList<String> stack = new LinkedList<>();
        int ptr = 0;
        while (ptr < s.length()) {
            char c = s.charAt(ptr);
            if (Character.isDigit(c)) {
                String num = "";
                while (Character.isDigit(c)) {
                    num = num + c;
                    c = s.charAt(++ptr);
                }
                stack.push(num);
            } else if (Character.isLetter(c) || '[' == c) {
                stack.push(String.valueOf(s.charAt(ptr++)));
            } else {
                // 不是字母或"["、不是数字，只剩下 "]"
                ++ptr;
                // 获取 [] 之间的字母
                LinkedList<String> sub = new LinkedList<>();
                while (!"[".equals(stack.peek())) {
                    sub.push(String.valueOf(stack.pop()));
                }
                Collections.reverse(sub);
                // 将"["出栈，并获取 "[" 之前的 数字
                stack.pop();
                StringBuilder sb = new StringBuilder();
                int num = Integer.parseInt(stack.pop());
                String temp = getString(sub);
                sb.append(String.valueOf(temp).repeat(Math.max(0, num)));
                stack.push(sb.toString());
            }
        }
        return getString(stack);
    }

    private String getString(LinkedList<String> sub) {
        String result = "";
        while (!sub.isEmpty()) {
            result = sub.pop() + result;
        }
        return result;
    }
}
