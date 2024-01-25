package com.first.family.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/lemonade-change/">860. 柠檬水找零</a>
 *
 * @description: 860. 柠檬水找零
 * @author: cuiweiman
 * @date: 2023/12/18 10:16
 */
public class No0860LemonadeChange {
    public static void main(String[] args) {
        No0860LemonadeChange demo = new No0860LemonadeChange();
        int[] array = {5, 5, 5, 10, 20};
        System.out.println(demo.lemonadeChange(array));
    }

    public boolean lemonadeChange(int[] bills) {
        int ten = 0, five = 0;
        for (int bill : bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
                if (five > 0) {
                    five--;
                    ten++;
                } else {
                    return false;
                }
            } else {
                if (ten > 0 && five > 0) {
                    ten--;
                    five--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean lemonadeChange2(int[] bills) {
        Map<Integer, Integer> charges = new HashMap<>(Map.of(5, 0, 10, 0, 20, 0));
        for (int bill : bills) {
            if (bill == 5) {
                charges.put(5, charges.get(5) + 1);
            } else if (bill == 10) {
                if (charges.get(5) >= 1) {
                    charges.put(5, charges.get(5) - 1);
                    charges.put(10, charges.get(10) + 1);
                } else {
                    return false;
                }
            } else {
                if (charges.get(10) >= 1 && charges.get(5) >= 1) {
                    charges.put(5, charges.get(5) - 1);
                    charges.put(10, charges.get(10) - 1);
                    charges.put(20, charges.get(20) + 1);
                } else if (charges.get(5) >= 3) {
                    charges.put(5, charges.get(5) - 3);
                    charges.put(20, charges.get(20) + 1);
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
