package com.first.family.xiaomi;

/**
 * @description:
 * @author: cuiweiman
 */
public class Test2 {
    public static void main(String[] args) {
        int[][] stores = {{1, 4}, {2, 2}, {3, 3}, {5, 1}};
        int nums = 4;
        int distance = latestDistance(stores, nums);
        System.out.println(distance);
    }

    public static int latestDistance(int[][] stores, int num) {
        int distance = 0;
        for (int i = 1; i < stores.length; i++) {
            distance += Math.abs(stores[i][0] - stores[i - 1][0]) + Math.abs(stores[i][1] - stores[i - 1][1]);
        }
        return distance;
    }
}
