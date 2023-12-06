package com.first.family.algorithm.bag;

import java.util.Arrays;

/**
 * 优化 1: 去除背包中存储的 物品信息，根据背包中物品的价值，回溯推算即可得知背包中的物品
 * <pre class="code">
 *         1   2   3   4    5    6     bag size
 * 1天安门  9*  9   9   9    9    9
 * 2故宫    9*  9   9   9    18   18
 * 3颐和园  9   9   18* 18   18   18
 * 4八达岭  9   16  18  25*  25   25
 * 5天坛    9   16  22  25   31*  31
 * 6圆明园  9   16  22  25   31*  33
 * 7恭王府  9   16  22  27   31   36*
 * goods
 *
 * </pre>
 * <p>
 * 回溯方法：从 二维数组 的 右下角 36 开始回溯。
 * 1. [7][6] != [[6][6], 表示 [7][6] 是放入了当前物品，goods[7];
 * 2. bagSize - goods[7].cost = 6-1 = 5，回溯到 [6][5]=31;
 * 3. [6][5]==[5][5]，表示 goods[6] 没有放入背包
 * 4. [5][5]!=[4][5]，表示 [5][5] 放入了当前物品 goods[5]; bagSize[5]-goods[5].size=5-1=4，即 [5][5]=[4][4]+goods[5].value;
 * 5. [4][4]!=[3][4], 表示 [4][4] 放入了当前物品 goods[4], bagSize[4]-goods[4].size=3, [4][4]=[3][3]+goods[4].value
 * 6. [3][3]!=[2][3]，表示 [3][3] 放入了当前物品 goods[3], bagSize[3]-goods[3].size=1, [3][3]=[2][1]+goods[3].value
 * 7. [2][1]==[1][1], 表示 goods[2] 没有放入背包
 * 8. [1][1]!=[0][1], goods[1]放入了背包, bagSize[1]-goods[1].size=0, [1][1]=[0][0]+goods[1].value;
 * 回溯完毕，最终背包中有 1,3,4,5,7: 9+9+7+6+5=18+18=36
 * <p>
 * 背包物品可以根据背包的value，进行回溯反推，从而得到 背包中存放了哪些价值的物品，因此可以去除 BagInfo 的集合属性
 * 那么 BagInfo 可以优化成一个 Integer 类型的二维数组
 *
 * @description: 二维数组优化 简单背包问题
 * @author: cuiweiman
 * @date: 2023/11/30 21:42
 */
public class TravelBagV2 {

    public static void main(String[] args) {
        TravelBagV2 demo = new TravelBagV2();
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
        demo.putBag2(goods, bagSize);
    }

    public void putBag2(Item[] goods, int bagSize) {
        int[] bagValue = new int[bagSize + 1];
        for (Item good : goods) {
            for (int size = bagSize; size > 0; size--) {
                if (size >= good.cost) {
                    bagValue[size] = Math.max(
                            bagValue[size],
                            good.value + bagValue[size - good.cost]
                    );
                }
            }
        }
        System.out.println(Arrays.toString(bagValue));
    }


    public void putBag(Item[] goods, int bagSize) {
        Integer[][] bagInfo = new Integer[goods.length][bagSize];
        for (int i = 0; i < goods.length; i++) {
            for (int j = 0; j < bagSize; j++) {
                // j 是从 0 开始的，spareSize 是尺寸的含义，需要 +1
                int spareSize = j + 1 - goods[i].cost;
                if (i == 0) {
                    if (spareSize < 0) {
                        bagInfo[i][j] = 0;
                    } else {
                        bagInfo[i][j] = goods[i].value;
                    }
                } else {
                    if (spareSize < 0) {
                        bagInfo[i][j] = bagInfo[i - 1][j];
                    } else {
                        int currValue = goods[i].value;
                        if (spareSize > 0) {
                            // spareSize 是尺寸的含义，作为数组下表时，需要 -1
                            currValue += bagInfo[i - 1][spareSize - 1];
                        }
                        if (currValue > bagInfo[i - 1][j]) {
                            bagInfo[i][j] = currValue;
                        } else {
                            bagInfo[i][j] = bagInfo[i - 1][j];
                        }
                    }
                }
            }
        }
        for (int i = 1; i <= bagSize; i++) {
            if (i == 1) {
                System.out.print("\t\t");
            }
            System.out.printf("%d\t", i);
        }
        System.out.println("\n-------------------------------");
        for (int i = 0; i < bagInfo.length; i++) {
            System.out.printf("%s\t", goods[i].name);
            for (int j = 0; j < bagInfo[i].length; j++) {
                System.out.printf("%s\t", bagInfo[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n==================================================\n");
        getBagInfo(goods, bagInfo);
    }

    public void getBagInfo(Item[] goods, Integer[][] bagInfo) {
        Item[] element = new Item[goods.length];
        int eleIndex = 0;
        // 从 二维数组的 最后一个元素 进行回溯
        int i = bagInfo.length - 1;
        int j = bagInfo[i].length - 1;
        while (i >= 0 && j >= 0) {
            if (bagInfo[i][j].equals(bagInfo[i - 1][j])) {
                // 当前元素 和 同列上一行 价值相等，表示当前物品没有放入背包，而是直接使用了同列上一行的方案
                i--;
                if (i == 0) {
                    // i=0 后，已经不能比较 [i-1][j] 的值了，到头了已经，回溯可以结束了
                    element[eleIndex++] = goods[i];
                    break;
                }
            } else {
                // 没有使用 同列上一行的价值方案，那么当前物品放进了背包
                element[eleIndex++] = goods[i];
                // 查询是否有剩余空间，并回溯其它物品
                int spareSize = j + 1 - goods[i].cost;
                if (spareSize > 0) {
                    // 背包出去当前物品空间外，还有剩余空间，才能放入其它物品
                    i = i - 1;
                    // j 指向剩余空间的值，但作为从 0 开始的下标，需要 -1
                    j = spareSize - 1;
                } else {
                    // 没有剩余空间了，背包里只放了当前物品
                    break;
                }
            }
        }
        for (int index = 0; index < eleIndex; index++) {
            System.out.println(element[index]);
        }
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
