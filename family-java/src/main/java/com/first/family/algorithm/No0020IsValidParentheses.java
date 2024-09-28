package com.first.family.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/valid-parentheses/">20. 有效的括号</a>
 *
 * @description: 20. 有效的括号
 * @author: cuiweiman
 * @date: 2023/11/23 16:23
 */
public class No0020IsValidParentheses {
    public static void main(String[] args) {
        String parentheses = "()";
        No0020IsValidParentheses demo = new No0020IsValidParentheses();
        boolean isValid = demo.isValid(parentheses);
        System.out.println(isValid);
        boolean isValid3 = demo.isValid3(parentheses);
        System.out.println(isValid3);
    }

    private boolean isValid3(String str) {
        char[] temp = new char[str.length()];
        int ptr = 0;
        for (char c : str.toCharArray()) {
            if (c == '{' || c == '[' || c == '(') {
                temp[ptr++] = c;
            } else if (ptr <= 0) {
                // 没有 左括号 或 没有字符
                return false;
            } else if (c == '}' && temp[--ptr] != '{') {
                return false;
            } else if (c == ']' && temp[--ptr] != '[') {
                return false;
            } else if (c == ')' && temp[--ptr] != '(') {
                return false;
            }
        }
        return ptr == 0;
    }

    private boolean isValid(String s) {
        char[] temp = new char[s.length()];
        int ptr = 0;
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                temp[ptr++] = c;
            } else if (ptr <= 0) {
                return false;
            } else if (c == ')' && temp[--ptr] != '(') {
                return false;
            } else if (c == ']' && temp[--ptr] != '[') {
                return false;
            } else if (c == '}' && temp[--ptr] != '{') {
                return false;
            }
        }
        return ptr == 0;
    }

    private boolean isValid2(String s) {
        // 1 <= s.length <= 104
        // s 仅由括号 '()[]{}' 组成
        Map<Character, Character> map = new HashMap<>(4);
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        LinkedList<Character> stack = new LinkedList();
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if (map.containsKey(aChar)) {
                stack.push(aChar);
            } else {
                if (stack.isEmpty()) {
                    // right 且 stack 为空
                    return false;
                }
                Character pop = stack.pop();
                Character character = map.get(pop);
                if (aChar != character) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
