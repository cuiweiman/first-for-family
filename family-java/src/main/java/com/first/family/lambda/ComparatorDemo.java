package com.first.family.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @description: ComparatorDemo
 * @author: cuiweiman
 * @date: 2023/2/15 15:33
 */
public class ComparatorDemo {

    public static void main(String[] args) {

        String[] arr = {"dsdk", "zyx", "abc", "xyz", "Zbc2349", "Adc2349"};
        Arrays.sort(arr);
        System.out.println(Arrays.stream(arr).collect(Collectors.toList()));

        // Arrays.sort(arr,(o1,o2)->o1.length()-o2.length());
        Arrays.sort(arr, Comparator.comparingInt(String::length));
        System.out.println(Arrays.stream(arr).collect(Collectors.toList()));

    }


}
