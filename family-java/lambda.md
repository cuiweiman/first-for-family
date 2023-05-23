[TOC]

# Lambda 表达式
> [马士兵视频](https://www.bilibili.com/video/BV11d4y117o7/?vd_source=0fea0ffbb5d1ad6f2980d72745e82403)

# 函数式接口

> **有且只有 一个 抽象方法 的接口。使用注解: @FunctionalInterface**

- Function
- Predicate
- Comparator
- Supplier / Consumer
- Runnable / Callable

***Lambda 实质是: 实现了 函数式接口 的 匿名子类 的对象***

# java.util.stream.Stream

**特点**

- 专注容器对象的 聚合操作
- 提供 串行、并行 的流式 处理方式 (Fork/Join 分治框架)
- 简洁高效

**流式中间操作API**
> 每个流式中间操作方法，响应内容是一个新的流

- map(): 将已有元素转换为另一个对象类型，一对一逻辑，返回新的stream流
- flatMap(): 将已有元素转换为另一个对象类型，一对多逻辑，即原来一个元素对象可能会转换为1个或者多个新类型的元素，返回新的stream流
- filter(): 按照条件过滤符合要求的元素， 返回新的stream流
- distinct(): 元素进行去重
- sorted(): 元素按照指定规则进行排序
- peek(): 对stream流中的每个元素进行逐个遍历处理，返回处理后的stream流
- limit(): 获取前n个元素
- skip(): 跳过前n个元素
- parallel(): 并行处理
- concat():  将两个流合并

**终结操作 Terminal Operation**
> 每个流只能使用一个终结API。

- forEach(): 无返回值，对元素进行逐个遍历，然后执行给定的处理逻辑
- toArray(): 将流转换为数组
- reduce(): 将一个Stream中的所有元素反复结合起来，得到一个结果
- collect(): 将流转换为指定的类型，通过Collectors进行指定
- min(): 获取元素最小值
- max(): 获取元素最大值
- count(): 返回最终的元素个数
- iterator(): 将流转换为Iterator对象
- anyMatch(): 判断是否有符合条件的元素
- allMatch(): 判断是否所有元素都符合条件
- noneMatch(): 判断是否所有元素都不符合条件
- findFirst(): 找到第一个符合条件的元素时则终止流处理
- findAny(): 找到任何一个符合条件的元素时则退出流处理，这个对于串行流时与findFirst相同，对于并行流时比较高效，任何分片中找到都会终止后续计算逻辑





































