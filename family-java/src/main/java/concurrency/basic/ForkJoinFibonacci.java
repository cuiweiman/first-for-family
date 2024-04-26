package concurrency.basic;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Fibonacci: 0、1、1、2、3、5、8、13、21、34 ... F(n-1)+F(n-2)
 *
 * @description: ForkJoin 优化 Fibonacci 数列，计算第1000个Fibonacci数列
 * @author: cuiweiman
 * @date: 2023/3/19 16:04
 */
public class ForkJoinFibonacci {
    private static final Long N = 10000L;

    public static void main(String[] args) {
        // 递归调用: StackOverflowError
        // long invoke = recursion(1000);
        // 非递归调用
        long invoke = fibonacci(N);
        System.out.println(invoke);

        // Fork/Join 优化: 10,000 触发  StackOverflowError
        main1();
    }

    public static void main1() {
        int coreSize = Runtime.getRuntime().availableProcessors();
        long start = System.currentTimeMillis();
        System.out.println("ForkJoin 开始 " + start);
        // Fork/Join 调用
        Long invoke = new ForkJoinPool(coreSize).invoke(new Fibonacci(N));
        System.out.println(invoke);
        long end = System.currentTimeMillis();
        long using = end - start;
        System.out.println("ForkJoin 结束 " + end + "  " + using);
    }


    /**
     * 递归
     */
    public static long recursion(Long n) {
        if (n == 2 || n == 1) {
            return 1;
        }
        return recursion(n);
    }

    /**
     * 非递归: 执行速度最快
     */
    public static long fibonacci(Long n) {
        long start = System.currentTimeMillis();
        System.out.println("非递归开始 " + start);
        long a = 1, b = 1, temp = 0;
        for (int i = 3; i <= n; i++) {
            temp = a + b;
            a = b;
            b = temp;
        }
        long end = System.currentTimeMillis();
        long using = end - start;
        System.out.println("非递归结束 " + end + "  " + using);
        return temp;
    }


    static ConcurrentMap<Long, Long> concurrentMap = new ConcurrentSkipListMap<>();

    static class Fibonacci extends RecursiveTask<Long> {
        final long n;

        Fibonacci(long n) {
            this.n = n;
        }

        protected Long compute() {
            if (n <= 1)
                return n;
            long n1 = this.n - 1;
            Fibonacci f1 = new Fibonacci(n1);
            f1.fork();
            long n2 = this.n - 2;
            Fibonacci f2 = new Fibonacci(n2);

            //缓存优化
            Long compute2 = concurrentMap.compute(n2, (key, oldValue) -> {
                if (Objects.nonNull(oldValue)) {
                    //缓存中存在，直接响应
                    return oldValue;
                }
                //缓存中不存在，则计算
                return f2.compute();
            });
            Long compute1 = concurrentMap.compute(n1, (key, oldValue) -> {
                if (Objects.nonNull(oldValue)) {
                    //存在，直接响应
                    return oldValue;
                }
                //不存在，计算
                return f1.join();
            });
            return compute2 + compute1;

            //正常计算
            //return f2.compute()+ f1.join();
        }
    }


}
