package com.first.family.algorithm;

/**
 * <a href="https://leetcode.cn/problems/house-robber-ii/description/">213. 打家劫舍 II</a>
 * <p>
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
 * <p>
 * 示例 1：
 * 输入：nums = [2,3,2]
 * 输出：3
 * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
 * <p>
 * 示例 2：
 * 输入：nums = [1,2,3,1]
 * 输出：4
 * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
 * 偷窃到的最高金额 = 1 + 3 = 4 。
 * <p>
 * 示例 3：
 * 输入：nums = [1,2,3]
 * 输出：3
 * <p>
 * 提示：
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 1000
 *
 * @description: 213. 打家劫舍 II , 相较于 No0198, 数组是首位相连的.
 * @author: cuiweiman
 * @date: 2024/3/24 22:44
 * @see No0198HouseRobber
 */
public class No0213HouseRobberII {

    public static void main(String[] args) {
        int[] nums = {2, 3, 2};
        // int[] nums = {1, 2, 3, 1};
        No0213HouseRobberII demo = new No0213HouseRobberII();
        int rob = demo.rob(nums);
        System.out.println(rob);
    }

    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        } else if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        } else if (nums.length == 3) {
            return Math.max(Math.max(nums[0], nums[1]), nums[2]);
        }
        // 从 0 开始，只能到 len-2 结束；从 1 开始 可以到 len-1 结束。取其中最大值
        return Math.max(doRob(nums, 0, nums.length - 2), doRob(nums, 1, nums.length - 1));
    }

    public int doRob(int[] nums, int start, int end) {
        // index-2
        int x = nums[start];
        // index-1
        int y = Math.max(nums[start], nums[start + 1]);
        int result = 0;
        for (int i = start + 2; i <= end; i++) {
            result = Math.max(x + nums[i], y);
            x = y;
            y = result;
        }
        return result;
    }

}
