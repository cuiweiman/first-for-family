[TOC]

# Java高并发

> - [汪文君视屏](https://www.bilibili.com/video/BV14b411m7UD)
> - [汪文君视屏2](https://www.bilibili.com/video/BV1xt41147xN/)
> - [汪文君视屏3](https://www.bilibili.com/video/BV1cb41127uP/)
> - [博客-湫兮若风](https://blog.csdn.net/weixin_38608626/category_9291549.html)

## 线程

- 进程: 应用程序
- 线程: 应用程序在运行过程中产生的线程，CPU调度的最小单位
- 并行: 多核CPU同时执行任务
- 并发: 单核CPU通过上下文切换方式调度执行任务

**线程生命周期**

1. 线程创建 New Thread thread = new Thread()
    1. 线程启动 Start thread.start()
2. 线程等待CPU调度 Runnable
3. 线程执行 Running（JVM调用thread.run()）
4. 线程阻塞 Blocked thread.sleep()
5. 线程终止 Terminated

线程的模式类似于『策略模式』思想，Run 方法将逻辑业务抽取出来，将线程执行的业务策略都由run方法执行。

**Daemon守护线程**

- ***用户线程***: daemon=false,主线程执行结束后，用户线程继续执行，执行结束后才退出JVM。
- ***守护线程***: daemon=true,主线程执行结束后，守护线程结束执行，并退出JVM。

## JVM 结构

- **程序计数器 Program Counter Register:**
    - 作用: 记录JVM需要执行的指令位置，方便CPU切换调度后线程可以在正确的位置继续执行。
    - 分支、循环、跳转、异常处理、线程恢复等基础功能都需要依赖这个计数器来完成。
    - 每个线程都有私有的程序计数器，生命周期与线程的一致。任何时间一个线程都只有一个方法在执行，即当前方法。程序计数器会存储当前线程正在执行的Java方法的JVM指令地址。
    - 是唯一没有OutOfMemoryError的区域。而本地栈等结构没有垃圾回收，但是有可能溢出。


- 本地方法区:
- 虚拟机栈:
- 方法区:
- 堆: 










