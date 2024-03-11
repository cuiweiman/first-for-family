package com.first.family.huawei;

import com.first.family.algorithm.common.MyUtil;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * <a href="https://leetcode.cn/problems/daily-temperatures/">739. 每日温度</a>
 * <p>
 * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，
 * 其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。
 * 如果气温在这之后都不会升高，请在该位置用 0 来代替。
 * <p>
 * 示例 1:
 * 输入: temperatures = [73,74,75,71,69,72,76,73]
 * 输出: [1,1,4,2,1,1,0,0]
 * <p>
 * 示例 2:
 * 输入: temperatures = [30,40,50,60]
 * 输出: [1,1,1,0]
 * <p>
 * 示例 3:
 * 输入: temperatures = [30,60,90]
 * 输出: [1,1,0]
 * <p>
 * 提示：
 * 1 <= temperatures.length <= 105
 * 30 <= temperatures[i] <= 100
 *
 * @description: 739. 每日温度
 * @author: cuiweiman
 * @date: 2024/2/18 17:49
 */
public class No0739DailyTemperatures {
    public static void main(String[] args) {
        // int[] temperatures = {30, 40, 50, 60};
        // int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
        int[] temperatures = {30, 60, 90};
        No0739DailyTemperatures demo = new No0739DailyTemperatures();
        int[] result = demo.dailyTemperatures(temperatures);
        MyUtil.intArrPrint("answer: ", result);
        int[] result2 = demo.dailyTemperatures2(temperatures);
        MyUtil.intArrPrint("answer: ", result2);
    }


    public int[] dailyTemperatures2(int[] temperatures) {
        int[] res = new int[temperatures.length];
        if (temperatures.length == 0) {
            return res;
        }
        // 存放暂时未找到下一个高温的下标
        Deque<Integer> stack = new LinkedBlockingDeque<>();
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > stack.peek()) {
                Integer pop = stack.pop();
                res[pop] = i - pop;
            }
            stack.push(i);
        }
        return res;
    }


    public int[] dailyTemperatures(int[] temperatures) {
        int[] answer = new int[temperatures.length];
        if (temperatures.length == 0) {
            return answer;
        }
        // stack 存储的是 下标
        LinkedBlockingDeque<Integer> stack = new LinkedBlockingDeque<>();
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                // 没有找到下一个高温的下标 pop，在此找到了符合条件的下标 i
                Integer pop = stack.pop();
                answer[pop] = i - pop;
            }
            stack.push(i);
        }
        return answer;
    }

}
