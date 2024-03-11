package com.first.family.huawei;

import java.util.Scanner;

/**
 * 给定一个字符串s，最多只能进行一次变换，返回变换后能得到的最小字符串（按照字典序进行比较）。
 * 变换规则：交换字符串中任意两个不同位置的字符。
 * <p>
 * 输入 abcdef
 * 输出 abcdef
 * 说明 已经是最小字符串
 * <p>
 * 输入 bcdefa
 * 输出 acdefb
 * 说明 a和b交换一次，得到最小字符串
 *
 * @description:
 * @author: cuiweiman
 * @date: 2024/3/9 12:44
 */
public class History2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String str = in.nextLine();
            System.out.println(test(str));
        }
    }

    public static String test(String str) {
        // 从头开始，找到第一个大的，再继续向后找到最小的
        char[] charArray = str.toCharArray();
        int max = 0;
        int i = 1;
        for (; i < charArray.length; i++) {
            if (charArray[i] > charArray[max]) {
                max = i;
            } else {
                break;
            }
        }
        if (i == charArray.length) {
            return str;
        }
        int min = 0;
        for (; i < charArray.length; i++) {
            if (charArray[i] < charArray[max]) {
                min = i;
            }
        }
        for (int j = 0; j < charArray.length; j++) {
            if (charArray[j] > charArray[min]) {
                char c = charArray[min];
                charArray[min] = charArray[j];
                charArray[j] = c;
                break;
            }
        }
        return new String(charArray);
    }

}
