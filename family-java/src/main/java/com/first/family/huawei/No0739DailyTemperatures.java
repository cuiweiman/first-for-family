package com.first.family.huawei;

import com.first.family.algorithm.common.MyUtil;

import java.util.Arrays;
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
    }


    public int[] dailyTemperatures(int[] temperatures) {
        int[] answer = new int[temperatures.length];
        if (temperatures.length <= 1) {
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

    public int[] dailyTemperatures2(int[] temperatures) {
        int[] ans = new int[temperatures.length];
        int[] next = new int[101];
        Arrays.fill(next, Integer.MAX_VALUE);
        for (int i = temperatures.length - 1; i >= 0; i--) {
            int temp = Integer.MAX_VALUE;
            for (int t = temperatures[i] + 1; t <= 100; t++) {
                if (temp > next[t]) {
                    temp = next[t];
                }
            }
            if (temp < Integer.MAX_VALUE) {
                ans[i] = temp - i;
            }
            next[temperatures[i]] = i;
        }
        return ans;
    }

    public int[] dailyTemperatures3(int[] temperatures) {
        int[] answer = new int[temperatures.length];
        if (temperatures.length == 0 || temperatures.length == 1) {
            return answer;
        }
        // 双指针: 超出时间限制::从左向右计算，有很多重复比较和计算的部分
        int curr = 0;
        int next = 1;
        for (int i = 0; i < temperatures.length; i++) {
            while (next < temperatures.length && temperatures[curr] > temperatures[next]) {
                next++;
            }
            if (next < temperatures.length) {
                answer[curr] = next - curr;
            } else {
                answer[curr] = 0;
            }
            curr++;
            next = curr + 1;
        }
        return answer;
    }
}
