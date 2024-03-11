package com.first.family.huawei.od;

/**
 * 攀登者喜欢寻找各种地图，并且尝试攀登到最高的山峰。
 * 地图表示为一维数组，数组的索引代表水平位置，数组的高度代表相对海拔高度。其中数组元素0代表地面。
 * 例如[0,1,2,4,3,1,0,0,1,2,3,1,2,1,0], 代表如下图所示的地图，地图中有两个山脉位置分别为 1,2,3,4,5和8,9,10,11,12,13，最高峰高度分别为 4,3。最高峰位置分别为3,10。
 * 一个山脉可能有多座山峰(高度大于相邻位置的高度，或在地图边界且高度大于相邻的高度)。
 *
 * @description:
 * @author: cuiweiman
 * @date: 2024/3/9 20:52
 */
public class Test1 {
    public static void main(String[] args) {
        // 3
        int[] nums = {0, 1, 4, 3, 1, 0, 0, 1, 2, 3, 1, 2, 1, 0};
        // int[] nums = {0, 1, 0};
        Test1 demo = new Test1();
        int i = demo.count_peaks(nums);
        System.out.println(i);
    }

    /**
     * 返回地图中山峰的数量
     *
     * @param hill_map int整型一维数组 地图数组(长度大于1)
     * @return int整型
     */
    public int count_peaks(int[] hill_map) {
        // write code here 双指针
        int result = 0;
        if (hill_map.length == 0) {
            return result;
        } else if (hill_map.length == 1) {
            return 1;
        }
        for (int i = 0; i < hill_map.length; i++) {
            if (i > 0 && i < hill_map.length - 1) {
                if (hill_map[i] > hill_map[i - 1] && hill_map[i] > hill_map[i + 1]) {
                    result++;
                }
            } else if (i == 0 && hill_map[i] > hill_map[i + 1]) {
                result++;
            } else if (i == hill_map.length - 1 && hill_map[i] > hill_map[i - 1]) {
                result++;
            }
        }
        return result;
    }


}
