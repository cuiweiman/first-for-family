package com.first.family.algorithm.bag;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 二维数组实现背包问题
 *
 * @description: 旅游背包问题，和游戏背包都一样，这个是根据自己理解写的
 * @author: cuiweiman
 * @date: 2023/11/29 19:24
 */
public class TravelBag {

    public static void main(String[] args) {
        TravelBag demo = new TravelBag();
        Item[] goods = {
                // 参观天安门，很高兴9天，但是需要花费0.5天，最小粒度是0.5天
                new Item("天安门", 9, 1),
                new Item("故  宫", 9, 4),
                new Item("颐和园", 9, 2),
                new Item("八达岭", 7, 1),
                new Item("天  坛", 6, 1),
                new Item("圆明园", 8, 2),
                new Item("恭王府", 5, 1)
        };
        // 背包大小为3天，按照0.5天最小粒度，可以划分6块区域
        Integer bagSize = 6;
        demo.putBag(goods, bagSize);
        System.out.println("\n======================================================================\n");
        demo.putBagCopy(goods, bagSize);
    }

    public void putBagCopy(Item[] goods, Integer bagSize) {
        BagInfo[][] bagInfo = new BagInfo[goods.length][bagSize];
        for (int i = 0; i < goods.length; i++) {
            for (int j = 0; j < bagSize; j++) {
                // 物品随着空间递增 直到背包极限 的过程
                int spareSpace = j + 1 - goods[i].cost;
                if (i == 0) {
                    if (spareSpace < 0) {
                        bagInfo[i][j] = new BagInfo(0, new HashSet<>());
                    } else {
                        bagInfo[i][j] = new BagInfo(goods[i].value, goods[i]);
                    }
                } else {
                    // 当前尺寸的空背包，放入当前物品后，能否放得下 以及 能否有剩余空间。
                    if (spareSpace < 0) {
                        // 放不下，直接取 同列上一行的 背包信息
                        bagInfo[i][j] = bagInfo[i - 1][j];
                    } else {
                        // 能放下，则需要判断，是否要在背包中先放下当前物品.因此可以使用当前物品的价值进行一个比较，取大的
                        int currValue = goods[i].value;
                        if (spareSpace > 0) {
                            // 在放下当前物品后，背包中还有剩余空间，那么计算剩余空间的最大值，可以通过回溯获得
                            // 背包二维数组中，第一个是物品，第二个是空间，上一行背包价值+当前背包价值
                            // spareSpace 是从 0 开始的，因此在作为下标时要-1，作为剩余空间时要+1
                            currValue += bagInfo[i - 1][spareSpace - 1].value;
                        }
                        // 得到当前物品在当前背包尺寸中的最大价值后，需要和同列上一行的背包信息比较，取最大者。
                        if (currValue > bagInfo[i - 1][j].value) {
                            // 当前物品在当前背包尺寸的方案价值更大
                            Set<Item> itemSet = new HashSet<>();
                            itemSet.add(goods[i]);
                            if (spareSpace > 0) {
                                itemSet.addAll(bagInfo[i - 1][spareSpace - 1].itemSet);
                            }
                            bagInfo[i][j] = new BagInfo(currValue, itemSet);
                        } else {
                            // 同列上一行的 背包方案价值更大
                            bagInfo[i][j] = bagInfo[i - 1][j];
                        }
                    }
                }
            }
        }
        bagPrint(bagInfo);
    }


    public void putBag(Item[] goods, Integer bagSize) {
        // 二维数组，列为 物品，行为 递增的 bagSize
        BagInfo[][] bagInfo = new BagInfo[goods.length][bagSize];
        // 先选取最基础的一个物品，然后递增 bagSize，每次判断能否放入背包，确保达到最大bagSize时，背包中的价值最大
        for (int i = 0; i < goods.length; i++) {
            for (int j = 0; j < bagSize; j++) {
                int spareSize = j + 1 - goods[i].cost;
                if (i == 0) {
                    if (spareSize >= 0) {
                        // 背包中的第一个物品，肯定能够放入背包，在只有一个物品时，随着bagSize增大无脑放
                        bagInfo[i][j] = new BagInfo(goods[i].value, goods[i]);
                    } else {
                        bagInfo[i][j] = new BagInfo(0, new HashSet<>());
                    }
                } else {
                    // 有多个物品，计算当前尺寸的空背包，放入当前物品后，能否放得下 以及 是否有剩余空间。
                    if (spareSize < 0) {
                        // 当前尺寸的空背包放入当前物品后，剩余空间负数，放不下，直接取 同列上一行的 背包信息
                        bagInfo[i][j] = bagInfo[i - 1][j];
                    } else {
                        // 当前物品可以放下，则需要判断，是否要在背包中先放下当前物品.可以使用当前物品的价值进行一个比较，取大的
                        // 另外，当前物品放在后，需要判断能否附加其它物品，使得空间利用充分，价值更大
                        int currValue = goods[i].value;
                        if (spareSize > 0) {
                            // 在放下当前物品后，背包中还有剩余空间，那么通过回溯获取剩余空间的最大价值
                            // 背包二维数组中，第一个是物品，第二个是空间，上一行背包价值+当前背包价值
                            // spareSpace 是从 0 开始的，因此在作为下标时要-1，作为剩余空间时要+1
                            currValue += bagInfo[i - 1][spareSize - 1].value;
                        }
                        // 得到当前背包尺寸中的最大价值后，需要和同列上一行的背包信息比较，取最大者。
                        if (currValue > bagInfo[i - 1][j].value) {
                            // 最新的价值较大，那么把最新的背包方案存好
                            Set<Item> itemSet = new HashSet<>();
                            itemSet.add(goods[i]);
                            if (spareSize > 0) {
                                Set<Item> otherSet = bagInfo[i - 1][spareSize - 1].itemSet;
                                itemSet.addAll(otherSet);
                            }
                            bagInfo[i][j] = new BagInfo(currValue, itemSet);
                        } else {
                            bagInfo[i][j] = bagInfo[i - 1][j];
                        }
                    }
                }
            }
        }
        bagPrint(bagInfo);
    }

    private void bagPrint(BagInfo[][] bagInfo) {
        for (int i = 0; i < bagInfo.length; i++) {
            for (int j = 0; j < bagInfo[i].length; j++) {
                System.out.println("goodsIndex=" + i + ", bagSize=" + j + ", res=" + bagInfo[i][j]);
            }
        }
    }

    private static class BagInfo {
        private final Integer value;
        private final Set<Item> itemSet;

        public BagInfo(Integer value, Set<Item> itemSet) {
            this.value = value;
            this.itemSet = itemSet;
        }

        public BagInfo(Integer value, Item item) {
            this.value = value;
            this.itemSet = new HashSet<>();
            this.itemSet.add(item);
        }

        @Override
        public String toString() {
            return "BagInfo { value = "
                    + this.value
                    + ", itemSet="
                    + itemSet.toString() + " }";
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
        public int hashCode() {
            return name.hashCode() ^ value.hashCode() ^ cost.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return obj instanceof Item item
                    && Objects.equals(this.name, item.name)
                    && Objects.equals(this.value, item.value)
                    && Objects.equals(this.cost, item.cost);
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
