package com.first.family.algorithm;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/stone-game/description/">877. 石子游戏</a>
 * <p>
 * Alice 和 Bob 用几堆石子在做游戏。一共有偶数堆石子，排成一行；每堆都有 正 整数颗石子，数目为 piles[i] 。
 * 游戏以谁手中的石子最多来决出胜负。石子的 总数 是 奇数 ，所以没有平局。
 * Alice 和 Bob 轮流进行，Alice 先开始 。
 * 每回合，玩家从行的 开始 或 结束 处取走整堆石头。 这种情况一直持续到没有更多的石子堆为止，此时手中 石子最多 的玩家 获胜 。
 * 假设 Alice 和 Bob 都发挥出最佳水平，当 Alice 赢得比赛时返回 true ，当 Bob 赢得比赛时返回 false 。
 * <p>
 * 示例 1：
 * 输入：piles = [5,3,4,5]
 * 输出：true
 * 解释：
 * Alice 先开始，只能拿前 5 颗或后 5 颗石子 。
 * 假设他取了前 5 颗，这一行就变成了 [3,4,5] 。
 * 如果 Bob 拿走前 3 颗，那么剩下的是 [4,5]，Alice 拿走后 5 颗赢得 10 分。
 * 如果 Bob 拿走后 5 颗，那么剩下的是 [3,4]，Alice 拿走后 4 颗赢得 9 分。
 * 这表明，取前 5 颗石子对 Alice 来说是一个胜利的举动，所以返回 true 。
 * <p>
 * 示例 2：
 * 输入：piles = [3,7,2,3]
 * 输出：true
 * <p>
 * 提示：
 * 2 <= piles.length <= 500
 * piles.length 是 偶数
 * 1 <= piles[i] <= 500
 * sum(piles[i]) 是 奇数
 * <p>
 * 思路：还剩下第 i~j 个下标的数组，始终 i<=j
 * dp[i][j] 表示当剩下的石子堆为下标 i 到下标 j 时，即在下标范围 [i,j] 中，当前玩家与另一个玩家的石子数量之差的最大值，
 * 注意当前玩家不一定是先手 Alice。
 * i<j 时 dp[i][j]=max(piles[i]−dp[i+1][j],piles[j]−dp[i][j−1])
 * i==j 时 dp[i][i]=piles[i]
 * <p>
 * 示例1中， piles = [5,3,4,5]
 * i==j 时，表示只剩下 一堆石子。玩家只能拿取这一堆石子。倒推
 * dp[0][0]=5;dp[1][1]=3;dp[2][2]=4;dp[3][3]=5;
 * 5 0 0 0
 * 0 3 0 0
 * 0 0 4 0
 * 0 0 0 5
 * <p>
 * 有两堆石子时（两个元素，统计先手拿的必赢结果）
 * i=2,j=3; dp[2][3] = max(4-5,5-4)=1;
 * 解释：先取左边i piles[i]=4, 后手只剩余 i+1~j 堆 dp[i+1][j]; 先取右边j piles[j]=5, 后手只剩 i~j-1 堆 dp[i][j-1];
 * i=1,j=2; dp[1][2]=max(2-4,4-3)=1
 * i=0,j=1; dp[0][1]=max(5-3,2-5)=2
 * 5 2 0 0
 * 0 3 1 0
 * 0 0 4 1
 * 0 0 0 5
 * ……
 *
 * @description: 877. 石子游戏
 * @author: cuiweiman
 * @date: 2024/3/20 14:32
 */
public class No0877StoneGame {
    public static void main(String[] args) {
        // int[] piles = {5, 3, 4, 5};
        int[] piles = {3, 7, 2, 3};
        No0877StoneGame demo = new No0877StoneGame();
        boolean result = demo.stoneGame(piles);
        System.out.println(result);
        boolean result2 = demo.stoneGame2(piles);
        System.out.println(result2);
    }

    public boolean stoneGame(int[] piles) {
        int length = piles.length;
        int[][] dp = new int[length][length];

        for (int i = 0; i < length; i++) {
            // 只有一堆石子时，没有选择。
            dp[i][i] = piles[i];
        }
        for (int i = length - 2; i >= 0; i--) {
            for (int j = i + 1; j < length; j++) {
                // 先手拿了 i 之后，后手只能拿 i+1~j 的最优解 dp[i+1][j]，再计算其差值
                // 先手拿了 j 之后，后手只能拿 i~j-1 的最优解 dp[i][j-1]，再计算其差值
                // j=i+1, 则 dp[i + 1][j] = dp[j][j]; dp[i][j-1] = dp[i][i]， 还可以优化成一维数组
                dp[i][j] = Math.max(piles[i] - dp[i + 1][j], piles[j] - dp[i][j - 1]);
            }
        }
        return dp[0][length - 1] > 0;
    }

    public boolean stoneGame2(int[] piles) {
        int length = piles.length;
        int[] dp = Arrays.copyOf(piles, length);
        for (int i = length - 2; i >= 0; i--) {
            for (int j = i + 1; j < length; j++) {
                dp[j] = Math.max(piles[i] - dp[j], piles[j] - dp[j - 1]);
            }
        }
        return dp[length - 1] > 0;
    }
}
