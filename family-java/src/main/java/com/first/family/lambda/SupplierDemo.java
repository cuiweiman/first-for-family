package com.first.family.lambda;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @description: SupplierDemo
 * @author: cuiweiman
 * @date: 2023/2/15 14:18
 * @see java.util.function.Supplier
 */
public class SupplierDemo {

    public static void main(String[] args) {
        int[] arr = {3, 6, 1, 0, 9, 7, 5};
        Supplier<Integer> maxSupplier = () -> Arrays.stream(arr).max().getAsInt();
        int max = getSomething(maxSupplier);
        System.out.println(max);

        Supplier<Integer> minSupplier = () -> Arrays.stream(arr).min().getAsInt();
        int min = getSomething(minSupplier);
        System.out.println(min);
    }

    private static int getSomething(Supplier<Integer> supplier) {
        return supplier.get();
    }

}
