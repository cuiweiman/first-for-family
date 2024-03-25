package com.first.family.algorithm;

/**
 * <a href="https://leetcode.cn/problems/house-robber/description/">198. 打家劫舍</a>
 * <p>
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
 * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 * <p>
 * 示例 1：
 * 输入：[1,2,3,1]
 * 输出：4
 * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
 * 偷窃到的最高金额 = 1 + 3 = 4 。
 * <p>
 * 示例 2：
 * 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 * <p>
 * 提示：
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 400
 *
 * @description: 198. 打家劫舍
 * @author: cuiweiman
 * @date: 2024/3/24 22:18
 * @see No0213HouseRobberII
 */
public class No0198HouseRobber {

    public static void main(String[] args) {
        // int[] nums = {1, 2, 3, 1};
        int[] nums = {2, 3, 2};
        No0198HouseRobber demo = new No0198HouseRobber();
        int robRecursion = demo.robRecursion(nums);
        System.out.println(robRecursion);
        int rob = demo.rob(nums);
        System.out.println(rob);
        int rob2 = demo.rob2(nums);
        System.out.println(rob2);
    }

    public int rob2(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return nums[0];
        } else if (len == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int first = nums[0];
        int second = Math.max(nums[0], nums[1]);
        for (int i = 2; i < len; i++) {
            int temp = second;
            second = Math.max(first + nums[i], second);
            first = temp;
        }
        return second;
    }

    /**
     * 动态规划 dp 数组
     */
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        } else if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        /*int[] dp = new int[nums.length];
        dp[0] = nums[0];
        // 取二者之间的最大值
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }
        return dp[nums.length - 1];*/


        // 存储 i-2 和 i-1 的值
        int x = nums[0];
        int y = Math.max(nums[0], nums[1]);
        int result = 0;
        for (int i = 2; i < nums.length; i++) {
            result = Math.max(y, x + nums[i]);
            x = y;
            y = result;
        }
        return result;

    }

    /**
     * 递归解法
     */
    public int robRecursion(int[] nums) {
        return doRob(nums, nums.length - 1);
    }

    public int doRob(int[] nums, int index) {
        if (nums == null || index < 0) {
            return 0;
        }
        if (index == 0) {
            return nums[0];
        }
        // 当前坐标为 index，那么是否打劫index，取决于 index-1 有没有打劫，若 index-1被打劫，则index不能被打劫。
        // 若 index-2 被打劫，那么可以打劫 index。
        // 打劫 index-1 还是 index-2 ，取决于谁是最优解
        return Math.max(doRob(nums, index - 1), doRob(nums, index - 2) + nums[index]);
    }
}
