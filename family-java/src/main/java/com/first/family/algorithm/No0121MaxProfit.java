package com.first.family.algorithm;

/**
 * <a href="https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/">121. 买卖股票的最佳时机</a>
 * <p>
 * 动态规划三步骤：
 * 1. 归纳DP数组的公式
 * 2. 初始化数组
 * 3. 遍历并计算结果
 *
 * @description: 力扣 121. 买卖股票的最佳时机
 * @author: cuiweiman
 * @date: 2023/12/4 15:28
 */
public class No0121MaxProfit {
    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        No0121MaxProfit demo = new No0121MaxProfit();
        int maxProfit = demo.maxProfit(prices);
        System.out.println(maxProfit);
        int maxProfit2 = demo.maxProfit2(prices);
        System.out.println(maxProfit2);
    }

    public int maxProfit2(int[] prices) {
        // 最小差数
        int min = prices[0];
        // 最大差距
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            max = Math.max(max, prices[i] - min);
            min = Math.min(min, prices[i]);
        }
        return max;
    }


    /**
     * {7, 1, 5, 3, 6, 4};
     * <p>
     * - dp[0][0]=0; dp[0][1]= -7;
     * - dp[i][0]=Math.max(dp[i-1][0], dp[i-1][1]+prices[i])
     * - dp[i][1]=Math.max(dp[i-1][1], -prices[i])
     *
     * <pre class="code">
     *              0       1
     *     7[0]     0      -7
     *     1[1]     0      -1
     *     5[2]     4      -1
     *     3[3]     4      -1
     *     6[4]     5      -1
     *     4[5]     3      -1
     * </pre>
     */
    public int maxProfit(int[] prices) {
        int length = prices.length;
        if (length < 2) {
            return 0;
        }
        int[][] dp = new int[length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[length - 1][0];
    }
}
