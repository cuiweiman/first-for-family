package com.first.family.algorithm;

/**
 * <a href="https://leetcode.cn/problems/valid-tic-tac-toe-state/">794. 有效的井字游戏</a>
 * <p>
 * 解题思路：分类+暴力穷举
 *
 * @description: 794. 有效的井字游戏
 * @author: cuiweiman
 * @date: 2024/3/21 22:32
 */
public class No0704ValidTicTacToe {
    public static void main(String[] args) {
        // String[] board = {"O  ", "   ", "   "};
        // String[] board = {"XOX"," X ","   "};
        String[] board = {"XOX", "O O", "XOX"};
        No0704ValidTicTacToe demo = new No0704ValidTicTacToe();
        boolean result = demo.validTicTacToe(board);
        System.out.println(result);
    }

    public boolean validTicTacToe(String[] board) {
        int xCount = 0, oCount = 0;
        for (String s : board) {
            for (char c : s.toCharArray()) {
                if ('X' == c) {
                    xCount++;
                } else if ('O' == c) {
                    oCount++;
                }
            }
        }
        if (xCount - oCount != 0 && xCount - oCount != 1) {
            return false;
        }
        if (win(board, "XXX") && xCount - oCount != 1) {
            return false;
        }
        if (win(board, "OOO") && xCount != oCount) {
            return false;
        }
        return true;
    }

    public boolean win(String[] board, String flag) {
        for (int i = 0; i < board.length; i++) {
            // 判断纵向
            if (flag.equals("" + board[0].charAt(i) + board[1].charAt(i) + board[2].charAt(i))) {
                return true;
            }
            // 判断横向
            if (flag.equals(board[i])) {
                return true;
            }
        }
        // 判断左到右对角 和 右到左对角
        if (flag.equals("" + board[0].charAt(0) + board[1].charAt(1) + board[2].charAt(2))) {
            return true;
        }
        if (flag.equals("" + board[0].charAt(2) + board[1].charAt(1) + board[2].charAt(0))) {
            return true;
        }
        return false;
    }
}
