package com.first.family.algorithm;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <a href="https://leetcode.cn/problems/implement-rand10-using-rand7/description/">470. 用 Rand7() 实现 Rand10()</a>
 * (randN() - 1) * N + randN() ==> [1, N^2] ==> randN^2()
 * (rand7-1) * 7 + rand7() = rand49()
 * <p>
 * 根据 rand3 实现 rand11：
 * 1. 根据rand3实现 rand27
 * 2. 根据rand27得到 rand22
 * 3. 使用rand22 取模得到 rand11
 *
 * <pre class="code">
 * 使用 Rand2 实现 Rand4
 * 1. Rand2 + Rand2: 只有 [2,4] 且概率不均匀
 *    1 + 1 = 2
 *    1 + 2 = 3
 *    2 + 1 = 3
 *    2 + 2 = 4
 * 2. (rand2-1) * 2 + rand2: [1,4] 且概率均匀
 *      0 + 1 = 1
 *      0 + 2 = 2
 *      2 + 1 = 3
 *      2 + 2 = 4
 * </pre>
 *
 * <pre class="code">
 * (rand7-1) * 7 + rand7()
 *      1   2   3   4   5   6   7
 * 0    1   2   3   4   5   6   7
 * 7    8   9   10  11  12  13  14
 * 14   15  16  17  18  19  20  21
 * 21   22  23  24  25  26  27  28
 * 28   29  30  31  32  33  34  35
 * 35   36  37  38  39  40  41  42
 * 42   43  44  45  46  47  48  49
 * 得到的 [1,49] 概率均匀，那么 得到 [1,40]的概率也均匀。
 * 40 是 10的整数倍，那么 mod 10 的结果也将是 均匀的。
 * </pre>
 *
 * @description: 470. 用 Rand7() 实现 Rand10()
 * @author: cuiweiman
 * @date: 2023/12/5 11:01
 */
public class No0470Solution {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        No0470Solution demo = new No0470Solution();
        for (int i = 1; i <= 100; i++) {
            int random = demo.rand10();
            System.out.printf("%d, ", random);
            if (i % 20 == 0) {
                System.out.println();
            }
        }

        demo.randomDemo();
        demo.concurrencyRandom();
        demo.secureRandom();
    }

    public int rand10() {
        int temp = 40;
        while (temp >= 40) {
            temp = (rand7() - 1) * 7 + rand7();
        }
        return temp % 10 + 1;
    }


    public int rand7() {
        // 1~7以内的随机整数
        Random random = new Random();
        return random.nextInt(7) + 1;
    }

    /**
     * rand3 实现 rand7
     */
    public int rand7V2() {
        int temp = (rand3() - 1) * 3 + rand3();
        while (temp > 7) {
            temp = (rand3() - 1) * 3 + rand3();
        }
        return temp;
    }

    public int rand3() {
        // 1~7以内的随机整数
        Random random = new Random();
        return random.nextInt(3) + 1;
    }

    public void randomDemo() {
        // Random默认使用系统当前时间的毫秒数作为种子，有规律可寻。
        System.out.println("======================= 伪随机:种子一样,随机数一样 =======================");
        Random random1 = new Random(1);
        Random random2 = new Random(1);
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s\t", random1.nextInt(10) == random2.nextInt(10));
        }
        System.out.println();
    }

    /**
     * 线程安全，随机种子默认也是以当前时间有关。
     */
    public void concurrencyRandom() {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        System.out.println(current.nextInt(10));
    }

    /**
     * 线程安全，安全性要求较高时使用
     */
    public void secureRandom() throws NoSuchAlgorithmException {
        // SecureRandom类收集了一些随机事件，比如鼠标点击，键盘点击等等
        // SecureRandom 使用这些随机事件作为种子。这意味着，种子是不可预测的
        // NativePRNG , SHA1PRNG
        SecureRandom instance = SecureRandom.getInstance("SHA1PRNG");
        System.out.println(instance.nextInt(10));
    }
}
