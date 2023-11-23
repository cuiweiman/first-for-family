package com.first.family.algorithm.common;

import java.util.Arrays;

/**
 * @description: 方便的工具
 * @author: cuiweiman
 * @date: 2023/8/29 17:28
 */
public class MyUtil {

    private MyUtil() {
    }

    public static void intArrPrint(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static void intArrPrint(String name, int[] arr) {
        System.out.println(name + ": " + Arrays.toString(arr));
    }
}
