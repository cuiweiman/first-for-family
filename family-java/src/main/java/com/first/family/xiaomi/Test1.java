package com.first.family.xiaomi;

/**
 * @description: 字符串转数字
 * @author: cuiweiman
 */
public class Test1 {
    public static void main(String[] args) {
        String str = "-123";
        int result = transfer(str);
        System.out.println(result);
    }

    private static int transfer(String str) {
        boolean flag = str.startsWith("-");
        if (flag) {
            str = str.substring(1);
        }
        char[] charArray = str.toCharArray();
        int result = 0;
        for (int i = 0; i < charArray.length; i++) {
            result = result + (charArray[i] - 48) * (int) Math.pow(10, charArray.length - i - 1);
        }
        if (flag) {
            result = -result;
        }
        return result;
    }

}
