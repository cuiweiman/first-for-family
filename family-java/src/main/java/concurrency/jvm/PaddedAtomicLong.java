package concurrency.jvm;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: 一个 Long 类型占用一个 CacheLine 解决 伪共享 问题
 * @author: cuiweiman
 * @date: 2023/12/29 17:15
 * @see com.lmax.disruptor.RingBuffer
 */
// @jdk.internal.vm.annotation.Contended
public class PaddedAtomicLong extends AtomicLong {
    /**
     * 填充
     */
    protected long p1, p2, p3, p4, p5, p6, p7;

    PaddedAtomicLong() {
        super();
    }

    PaddedAtomicLong(Long value) {
        super(value);
    }

    /**
     * To prevent GC optimizations for cleaning unused padded references
     * 避免: JVM 可能会清除无用字段或重排无用字段的位置，可能无形中又会引入伪共享。我们也没有办法指定对象在堆内驻留的位置。
     */
    public long sumPaddingToPreventOptimization() {
        // return p1 + p2 + p3 + p4 + p5 + p6 + p7;
        return super.longValue();
    }
}
