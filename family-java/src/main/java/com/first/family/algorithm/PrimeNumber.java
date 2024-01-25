package com.first.family.algorithm;

/**
 * 统计n以内的素数个数，0、1除外，例：输入：100 输出：25
 * 1. 暴力算法：i 从 2 开始计数并判断，使用 2~根号i 作为除数。根据数学知识 x * y = y * x 可知 计算到 根号i 即可。素数除了2以外都是奇数，可以只寻找奇数中的素数
 * 2. 埃氏筛选
 * <p>
 * 素数：除了0和1之外，其它只能被1和自身整除的自然数
 *
 * @description: 素数统计
 * @author: cuiweiman
 * @date: 2023/12/11 10:50
 */
public class PrimeNumber {

    public static void main(String[] args) {
        PrimeNumber demo = new PrimeNumber();
        int n = 100;
        System.out.println(demo.bruteForce(n));
        System.out.println(demo.eratosthenes(n));

    }


    /**
     * 埃氏筛选
     * 使用一个数组，存储素数和合数的标识。
     * 1. 默认数组全部为false，使用false代表素数。因为布尔数组模式是false，不用重新赋值
     * 2. 开始遍历2到n个数字，计算 i*j 得到合数，将合数标识为true，那么数组将区分开合数和素数
     * 3. 根据计算结果，逐个统计素数个数
     */
    public int eratosthenes(int n) {
        // false 表示素数
        boolean[] primeNumber = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!primeNumber[i]) {
                // false-素数，!false
                count++;
                // 从 i*i开始搜寻合数，能够减少循环次数
                for (int j = i * i; j < n; j += i) {
                    // j 是合数下标位，实现系数递增：2*i, 3*i, 4*i
                    // 2*2，3*2，4*2……
                    // 2*3，3*3，4*3……
                    // 2*4，3*4，4*4……
                    primeNumber[j] = true;
                }
            }
        }
        return count;
    }

    /**
     * 暴力算法_从奇数中寻找素数的优化
     * 除了偶数 2 是素数之外，素数都是奇数，因此直接在 奇数中 查找素数。
     */
    public int bruteForce(int n) {
        int count = 0;
        if (n >= 2) {
            // 当范围大于等于2时，计数器直接从1开始计数，搜寻时不再搜寻 素数2。
            count = 1;
        }
        for (int i = 3; i < n; i += 2) {
            boolean flag = true;
            // 计算到 根号i 即可， x*y==y*x
            for (int j = 3; j * j <= i; j += 2) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                count++;
            }
        }
        return count;
    }

}
