package com.first.family.huawei;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 给定两个只包含数字的数组a，b，调整数组 a 里面的数字的顺序，使得尽可能多的a[i] > b[i]。
 * 数组a和b中的数字各不相同。
 * 输出所有可以达到最优结果的a数组的结果。
 * <p>
 * 输入的第一行是数组 a 中的数字，其中只包含数字，每两个数字之间相隔一个空格，a数组大小不超过10。
 * 输入的第二行是数组 b 中的数字，其中只包含数字，每两个数字之间相隔一个空格，b数组大小不超过10。
 * 输出所有可以达到最优结果的 a 数组的数量。
 * <p>
 * 输入
 * 11 8 20
 * 10 13 7
 * 输出
 * 1
 * 说明 最优结果只有一个，a = [11, 20, 8]，故输出1
 * <p>
 * 输入
 * 11 12 20
 * 10 13 7
 * 输出
 * 2
 * 说明
 * 有两个a数组的排列可以达到最优结果，[12, 20, 11] 和 [11, 20, 12]，故输出2
 * <p>
 * 输入
 * 1 2 3
 * 4 5 6
 * 输出
 * 6
 * 说明 a输定了，只能输出所有全排列
 *
 * @description: 田忌赛马2
 * @author: cuiweiman
 * @date: 2024/3/9 17:15
 */
public class History4 {
    public static void main(String[] args) {
        int[] a = {11, 12, 20};
        int[] b = {10, 13, 7};
        int test = test(a, b);
        System.out.println(test);
    }

    public static int test(int[] a, int[] b) {
        int[] aSort = Arrays.copyOf(a, a.length);
        int[] bSort = Arrays.copyOf(b, b.length);
        Arrays.sort(aSort);
        Arrays.sort(bSort);

        LinkedList<Integer> useless = new LinkedList<>();
        Map<Integer, LinkedList<Integer>> map = new HashMap<>();
        for (int i : bSort) {
            map.put(i, new LinkedList<>());
        }
        int index = 0;
        for (int aEle : aSort) {
            if (aEle > bSort[index]) {
                map.get(bSort[index]).addFirst(aEle);
            } else {
                useless.addFirst(aEle);
            }
        }

        return 0;
    }


}
