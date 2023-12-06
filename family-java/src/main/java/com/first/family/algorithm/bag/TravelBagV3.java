package com.first.family.algorithm.bag;

import java.util.Arrays;

/**
 * 优化 一维数组实现：根据二维数组回溯的过程，从背包最大尺寸倒推，逐步修改 一维数组中的最优值，最终得到结果
 * <p>
 * 恭王府,天坛,八达岭,颐和园,天安门
 *
 * @description: 一维数组优化 简单背包问题
 * @author: cuiweiman
 * @date: 2023/11/30 21:42
 */
public class TravelBagV3 {

    public static void main(String[] args) {
        TravelBagV3 demo = new TravelBagV3();
        Item[] goods = {
                // 参观天安门，很高兴9天，但是需要花费0.5天，最小粒度是0.5天
                new Item("天安门", 9, 1),
                new Item("故  宫", 9, 4),
                new Item("八达岭", 7, 1),
                new Item("颐和园", 9, 2),
                new Item("天  坛", 6, 1),
                new Item("恭王府", 5, 1),
                new Item("圆明园", 8, 2)
        };
        int bagSize = 6;
        demo.putBag(goods, bagSize);
    }

    public void putBag(Item[] goods, int bagSize) {
        int[] bagInfo = new int[bagSize + 1];
        // 从哪个物品开始都无所谓
        for (Item good : goods) {
            // 从最大背包尺寸开始，进行回溯倒推。在理解二维的基础上，比较好理解一维的回溯逻辑
            for (int currSize = bagSize; currSize > 0; currSize--) {
                if (currSize >= good.cost) {
                    int currentValue = good.value + bagInfo[currSize - good.cost];
                    if (currentValue > bagInfo[currSize]) {
                        // 更新背包的价值
                        bagInfo[currSize] = currentValue;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(bagInfo));
    }

    private static class Item {
        private final String name;
        private final Integer value;
        private final Integer cost;

        public Item(String name, Integer value, Integer cost) {
            this.name = name;
            this.value = value;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Item { name=" + this.name
                    + ", value=" + this.value
                    + ", cost=" + this.cost
                    + " }";
        }
    }


}
