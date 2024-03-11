package com.first.family.huawei.od;

import java.util.Objects;
import java.util.Scanner;

/**
 * 特定大小的停车场，数组cars[]表示，其中1表示有车，0表示没车。
 * 车辆大小不一，小车占一个车位（长度1），货车占两个车位（长度2），卡车占三个车位（长度3），统计停车场最少可以停多少辆车，返回具体的数目。
 * <p>
 * in 1,0,1
 * out 2
 * 1个小车占第1个车位
 * 第二个车位空
 * 1个小车占第3个车位
 * 最少有两辆车
 * <p>
 * 1,1,0,0,1,1,1,0,1
 * 3
 * 1个货车占第1、2个车位
 * 第3、4个车位空
 * 1个卡车占第5、6、7个车位
 * 第8个车位空
 * 1个小车占第9个车位
 * 最少3辆车
 *
 * @description:
 * @author: cuiweiman
 * @date: 2024/3/9 20:53
 */
public class Test2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String carsLine = in.nextLine();
        int[] cars = new int[0];
        String[] split = carsLine.split(",");
        if (split.length > 0) {
            cars = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                if (Objects.equals(split[i], "0") || Objects.equals(split[i], "1")) {
                    cars[i] = Integer.parseInt(split[i]);
                }
            }
        }
        int i = parkingCount(cars);
        System.out.println(i);
    }

    public static int parkingCount(int[] car) {
        int result = 0;
        int count = 0;
        for (int i = 0; i < car.length; i++) {
            if (car[i] == 1) {
                count++;
                if (count == 1) {
                    result++;
                } else if (count == 3) {
                    count = 0;
                }
            } else {
                count = 0;
            }
        }
        return result;
    }


}









