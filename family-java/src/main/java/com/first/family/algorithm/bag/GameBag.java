package com.first.family.algorithm.bag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 二维数组实现背包问题
 *
 * @description: 游戏背包问题
 * @author: cuiweiman
 * @date: 2023/11/29 19:24
 */
public class GameBag {
    public static void main(String[] args) {
        GameBag demo = new GameBag();
        Element[] gameElements = {
                new Element("杯", 15, 1),
                new Element("盆", 20, 3),
                new Element("桶", 30, 4)
        };
        int gameBagSize = 4;
        ArrayElement[][] gameRes = demo.putBag(gameElements, gameBagSize);
        for (int i = 0; i < gameElements.length; i++) {
            for (int j = 0; j < gameBagSize; j++) {
                System.out.println("i=" + i + ", j=" + j + ", res=" + gameRes[i][j]);
            }
        }

        demo.putBag2(gameElements, gameBagSize);
    }

    public void putBag2(Element[] goods, int bagSize) {
        int[] bagValue = new int[bagSize + 1];
        for (Element good : goods) {
            for (int currSize = bagSize; currSize > 0; currSize--) {
                if (currSize >= good.cost) {
                    bagValue[currSize] = Math.max(
                            bagValue[currSize],
                            good.value + bagValue[currSize - good.cost]
                    );
                }
            }
        }
        System.out.println(Arrays.toString(bagValue));
    }


    public ArrayElement[][] putBag(Element[] goods, int bagSize) {

        ArrayElement[][] resultArr = new ArrayElement[goods.length][bagSize];
        // 构造二维数组，按照行，由小到大逐步填充bagSize
        for (int i = 0; i < goods.length; i++) {
            for (int j = 0; j < bagSize; j++) {
                int spareSpace = j + 1 - goods[i].cost;
                if (i == 0) {
                    if (spareSpace >= 0) {
                        // 第一行数据做特殊处理,不管能否放下物品，都暂时放下第一个物品
                        resultArr[i][j] = new ArrayElement(goods[i].value, goods[i]);
                    } else {
                        resultArr[i][j] = new ArrayElement(0, new HashSet<>());
                    }
                } else {
                    // 计算当前单元格，能否放在当前物品
                    if (spareSpace < 0) {
                        // 放不下，直接使用同列上一行的数据
                        resultArr[i][j] = resultArr[i - 1][j];
                    } else {
                        // 能放下，需要和同列上一行比较，当前商品价值，哪个更大
                        int preRow = i - 1;
                        int preRowValue = resultArr[preRow][j].value;
                        // 当前商品的价值
                        int currentGoodsValue = goods[i].value;
                        if (spareSpace > 0) {
                            // 是否有剩余空间，如果有，获得剩余空间的最大值
                            currentGoodsValue += resultArr[preRow][spareSpace - 1].value;
                        }
                        if (preRowValue >= currentGoodsValue) {
                            // 当前价值，没有同列上一行的价值大，那么使用同列上一行的背包策略
                            resultArr[i][j] = resultArr[preRow][j];
                        } else {
                            // 当前价值比较大
                            if (spareSpace == 0) {
                                // 空间只能存放当前物品
                                resultArr[i][j] = new ArrayElement(currentGoodsValue, goods[i]);
                            } else {
                                // 除了能够放入当前物品，还有空间剩余，将当前物品添加到
                                Set<Element> newElement = resultArr[preRow][spareSpace - 1].elementSet;
                                newElement.add(goods[i]);
                                resultArr[i][j] = new ArrayElement(currentGoodsValue, newElement);
                            }
                        }
                    }
                }
            }
        }
        return resultArr;
    }


    private static class ArrayElement {
        /**
         * 计算后的数组元素值
         */
        private int value;
        /**
         * 背包中放入的物品
         */
        private Set<Element> elementSet;

        public ArrayElement(int value, Element element) {
            this.value = value;
            this.elementSet = new HashSet<>();
            elementSet.add(element);
        }

        public ArrayElement(int value, Set<Element> elementSet) {
            this.value = value;
            this.elementSet = elementSet;
        }

        @Override
        public String toString() {
            return "ArrayElement{ value=" + value +
                    ", elements=" + elementSet +
                    '}';
        }
    }

    /**
     * 放入背包的物品类
     */
    private static class Element {
        private final String name;
        private final int value;
        /**
         * 物品的花费
         */
        private final int cost;

        public Element(String name, int value, int cost) {
            this.name = name;
            this.value = value;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    ", cost=" + cost +
                    '}';
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }


}
