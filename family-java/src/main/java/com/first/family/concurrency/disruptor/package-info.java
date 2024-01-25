/**
 * Disruptor
 * java服务-高性能队列-Disruptor使用场景:  https://blog.csdn.net/philip502/article/details/125332082
 * 美团技术团队 :: 高性能队列——Disruptor  https://tech.meituan.com/2016/11/18/disruptor.html
 * Disruptor 4.0.0 版本调整项 https://www.jdon.com/68912.html
 * Disruptor API: https://lmax-exchange.github.io/disruptor/javadoc/com.lmax.disruptor/com/lmax/disruptor/package-summary.html
 * <p>
 * Disruptor 4.0.0:
 * 1. 现在最低 Java 版本为 11
 * 2. 问题#323 - WorkerPool, WorkProcessor 被删除，不再存在 Disruptor::handleEventsWithWorkerPool
 * 3. Disruptor 使用的构造函数 Executor 已被删除，ThreadFactory代替使用。
 * 4. 将事件处理扩展接口汇总到EventHandler：BatchStartAware、LifecycleAware、SequenceReportingEventHandler
 * 5. FatalExceptionHandler 现在 IgnoreExceptionHandler 使用 JDK 9 Platform Logging API，即System.Logger
 * 6. 添加倒带批量功能BatchEventProcessor
 * 7. 添加最大批量大小参数BatchEventProcessor，EventHandler::onBatchStart 现在同时获取 batchSize 和 queueDepth（之前它batchSize报告了队列深度）
 * 8. 添加了文档EventPoller
 * 9. Util::log2如果传递一个非正参数则抛出异常
 * 10. 已弃用ThreadHints.onSpinWait()，已弃用Disruptor.handleExceptionsWith()- 自 2015 年以来，javadoc 已弃用此属性，但代码中并未弃用
 * 11. 删除了以前不推荐使用的方法：RingBuffer.resetTo()，ConsumerRepository.getLastSequenceInChain()
 * <p>
 * 等待策略
 * 1. BlockingWaitStrategy：用了ReentrantLock的等待&&唤醒机制实现等待逻辑，是默认策略，比较节省CPU
 * 2. BusySpinWaitStrategy：持续自旋，JDK9之下慎用（最好别用）
 * 3. DummyWaitStrategy：返回的Sequence值为0，正常环境是用不上的
 * 4. LiteBlockingWaitStrategy：基于BlockingWaitStrategy，在没有锁竞争的时候会省去唤醒操作，但是作者说测试不充分，不建议使用
 * 5. TimeoutBlockingWaitStrategy：带超时的等待，超时后会执行业务指定的处理逻辑
 * 6. LiteTimeoutBlockingWaitStrategy：基于TimeoutBlockingWaitStrategy，在没有锁竞争的时候会省去唤醒操作
 * 7. 推荐 SleepingWaitStrategy：三段式，第一阶段自旋，第二阶段执行Thread.yield交出CPU，第三阶段睡眠执行时间，反复的的睡眠
 * 8. 推荐 YieldingWaitStrategy：二段式，第一阶段自旋，第二阶段执行Thread.yield交出CPU
 * 9. PhasedBackoffWaitStrategy：四段式，第一阶段自旋指定次数，第二阶段自旋指定时间，第三阶段执行Thread.yield交出CPU，第四阶段调用成员变量的waitFor方法，这个成员变量可以被设置为BlockingWaitStrategy、LiteBlockingWaitStrategy、SleepingWaitStrategy这三个中的一个
 */
package com.first.family.concurrency.disruptor;