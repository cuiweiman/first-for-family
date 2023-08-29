- [ShardingSphere官方文档](https://shardingsphere.apache.org/document/current/cn/overview/)
- [尚硅谷ShardingSphere5](https://www.bilibili.com/video/BV1ta411g7Jf/)
- [Sharding5的分片策略配置](https://blog.csdn.net/qq_34279995/article/details/122421989)

[TOC]

# 分库分表

## 何时分库分表

1. 三年内单表数据量大于 500 万或者单表数据文件大于 2G。
2. 持续高速增长的数据，需要尽早考虑分库分表，且预留空间。

> - 分片健变更频繁，需要数据迁移的，不适合分库分表
> - 业务逻辑与分片逻辑绑定，限制 SQL 执行，不建议分库分表

## 垂直拆分

**垂直分库**

- 按照业务将表分类，分不到不同的数据库上；
- 每个库可以位于不同的服务器，转库专用；
- 取决于业务的规划和系统边界的划分；
- 高并发场景下，垂直分库能够在一定程度上提升 IO 访问效率和数据库的连接数；

**垂直分表**

- 将复杂的表按照字段分成多张表，每张表存储部分字段；
- 一定程度上能够提升性能，但操作范围依然在一台服务器上，会竞争数据库连接；

## 水平拆分

- 在一张表数据量大、访问量大的高并发场景喜爱，将该表的数据拆分到多张表中，多张表的表结构都是相同的；
- 水平拆分的多张表，可以在不同服务器的不同的数据库中；
- 能解决单库存储量的瓶颈和单库单表的性能 0 瓶颈，但是数据的访问将需要额外的路由工作，提升了系统复杂度；

## 常用分片策略

- 取模算法
    - 根据列进行 hash 求值来取模，数据存放比较均匀，扩容时需要大量数据的迁移。
- 范围限定算法
    - 比如按年份、时间等策略路由到目标数据库或表，扩容时不需要迁移数据，数据容易出现倾斜。
- ***取模算法与范围限定算法融合***
    - 整体上，按照范围进行分片，可以保证扩容时，老数据不需要迁移；
    - 在范围分片内，按照取模算法进行分片，使得在范围内的数据分布均匀；
- 预定义算法
    - 根据业务场景，灵活定制分片策略
    - 事先规划好具体库或表的数量，直接路由到指定的库或表中

## 处理分片的位置

分库分表的 核心概念是 分片

- **客户端分片**: 阿里巴巴的 TDDL(为开源)、 Sharding-JDBC
    - 分片逻辑的维护工作在前置的客户端进行管理和维护，在客户端决定每次 SQL 执行对应的目标库表；
    - 缺点：分片逻辑入侵业务代码；问题处理需要业务开发人员排查，而不是中间件团队。
    - 改进策略：重写 JDBC 协议，解耦数据库中间件和业务代码。如 ShardingSphere
- **代理服务器分片**: 阿里巴巴 Cobar、MyCat、Sharding-Proxy模块
    - 应用层和数据库层之间通过 代理服务器 连接，进行解耦。
    - 缺点：多了一层网络传输，影响性能；
- **分布式数据库**: TiDB
    - 内置了 数据分片及分布式事务的逻辑功能，对业务开发人员透明

## 分库分表的问题

- 如何对多数据库进行高效管理
- 如何进行跨节点关联查询
- 如何实现跨节点的分页和排序操作
- 如何生成全局唯一的主键
- 如何确保分布式事务的一致性、安全性和准确性
- 如何对数据进行迁移
- SQL 执行过程中的路由问题
- 各个节点都只包含一部分查询结果，查询结果的归并问题
- ……

# ShardingSphere5 简介

> 关系型数据库中间件

可以将任意数据库转换为分布式数据库，并通过数据分片、弹性伸缩、加密等能力对原有数据库进行增强。

- **Sharding-JDBC** 定位为轻量级 Java 框架，在 Java 的 JDBC 层提供的额外服务。
    - 适用于任何基于 JDBC 的 ORM 框架，如：JPA, Hibernate, Mybatis, Spring JDBC Template 或直接使用 JDBC；
    - 支持任何第三方的数据库连接池，如：DBCP, C3P0, BoneCP, HikariCP 等；
    - 支持任意实现 JDBC 规范的数据库，目前支持 MySQL，PostgreSQL，Oracle，SQLServer 以及任何可使用 JDBC 访问的数据库。
- **Sharding-Proxy** 定位为透明化的数据库代理端，通过实现数据库二进制协议，对异构语言提供支持。
    - 向应用程序完全透明，可直接当做 MySQL/PostgreSQL 使用；
    - 兼容 MariaDB 等基于 MySQL 协议的数据库，以及 openGauss 等基于 PostgreSQL 协议的数据库；
    - 适用于任何兼容 MySQL/PostgreSQL 协议的的客户端，如：MySQL Command Client, MySQL Workbench, Navicat 等。
- Sharding-Sidecar: 规划中。

|       | ShardingSphere-JDBC | ShardingSphere-Proxy |
|-------|---------------------|----------------------|
| 数据库   | 任意                  | MySQL/PostgreSQL     |
| 连接消耗数 | 高                   | 低                    |
| 异构语言  | 仅 Java              | 任意                   |
| 性能    | 损耗低                 | 损耗略高                 |
| 无中心化  | 是                   | 否                    |
| 静态入口  | 无                   | 有                    |

## 产品特性

- **数据分片**: 是应对海量数据存储与计算的有效手段。
    - ShardingSphere 基于底层数据库提供分布式数据库解决方案，可以水平扩展计算和存储。
- **分布式事务**: 是保障数据库完整、安全的关键技术，也是数据库的核心技术。
    - 基于 XA 和 BASE 的混合事务引擎，ShardingSphere 提供在独立数据库上的分布式事务功能，保证跨数据源的数据安全。
- **读写分离**: 是应对高压力业务访问的手段。
    - 基于对 SQL 语义理解及对底层数据库拓扑感知能力，ShardingSphere
      提供灵活的读写流量拆分和读流量负载均衡。
- **数据迁移**: 是打通数据生态的关键能力。
    - ShardingSphere 提供跨数据源的数据迁移能力，并可支持重分片扩展。
- **联邦查询**: 是面对复杂数据环境下利用数据的有效手段。
    - ShardingSphere 提供跨数据源的复杂查询分析能力，实现跨源的数据关联与聚合。
- **数据加密**: 是保证数据安全的基本手段。
    - ShardingSphere 提供完整、透明、安全、低成本的数据加密解决方案。
- **影子库**: 在全链路压测场景下，ShardingSphere 支持不同工作负载下的数据隔离，避免测试数据污染生产环境。

## ShardingSphere5 核心功能

- 微内核架构
    - 多插件可插拔，方便自定义功能
- 分布式主键
    - 默认 SnowFlake
- 分片引擎
    - 数据分片: 支持垂直拆分、水平拆分
    - 读写分离: 基于数据库主从架构的读写分离机制，
- 分布式事务
    - 支持本地事务、基于 XA 两阶段提交的 强一致性事务、基于 Base 的柔性 最终一致性事务；
        - XA 强一致性事务管理器: 内置集成的 Atomikos、Narayana 和 Bitronix 等技术实现；
        - 最终一致性事务管理器: 内部整合 Seata 来提供柔性事务功能；
    - ShardingSphere 抽象了标准化的事务处理接口，通过分片管理器 ShardingSphereTransactionManager 统一管理。
- 数据库治理和集成
    - 治理: ShardingSphere 提供了注册中心(Nacos 和 Zookeeper)、配置中心等一系列功能来支持数据库治理；
    - 配置: 基于 yaml 格式或 xml 格式的配置文件维护配置信息；还提供配置信息动态化的管理机制，支持数据源、表、分片、读写分离策略的动态切换。
- 链路跟踪
    - Sql 解析与执行过程中，会将运行时的数据通过标准协议提交到链路跟踪系统。
- 数据脱敏
    - 保证数据的访问安全，对原始 SQL 进行改写，实现对原文数据的加密；

## ShardingSphere 重写 JDBC 规范

```
// JDBC 规范的 API 操作流程
PooledDataSource datasource = new PooledDataSource();
datasource.setDriver("");
datasource.setUrl("");
datasource.setUsername("");
datasource.setPassword("");
Connection connection = dataSource.getConnection();
PreparedStatement statement = connection.prepareStatement("select * from User");
ResultSet resultSet = statement.executeQuery();
while(resultSet.next()){ }
resultSet.close();
statement.close();
connection.close();
```

> - JDBC 核心接口：DataSource、Connection、Statement、PreparedStatement、ResultSet
> - ShardingSphere 在这些接口上，基于适配器模式对 JDBC 规范进行了重写

- ShardingSphereDataSource
- ShardingSphereConnection
- ShardingSphereStatement
- ShardingSpherePreparedStatement

# ShardingSphere5的使用

shardingjdbc 通过分片策略 + 分片算法完成数据分片;

shardingjdbc 提供了4种分片策略，可以根据需求选择合适的策略配置，当然如果提供的都不能满足需求，也可以自定义策略。

分片策略的接口: org.apache.shardingsphere.sharding.route.strategy.ShardingStrategy

**在选择分表或分库的策略的时候，主要是针对分片键来决定的。根据分片键的一个或多个或不固定就可以选择配置对应的策略**

| 分片策略 | 配置key    | 对应的实现类                   | 适用场景   | 说明              |
|------|----------|--------------------------|--------|-----------------|
| 不分片  | none     | NoneShardingStrategy     | 不分片    | 不需要分片的时候配置此策略   |
| 标准分片 | standard | StandardShardingStrategy | 单个分片键  | 只有一个分片键的时候使用此策略 |
| 组合分片 | complex  | ComplexShardingStrategy  | 多个分片键  | 表有多个分片键的时候使用此策略 |
| 命中分片 | hint     | HintShardingStrategy     | 非固定分片键 | 比较灵活的分片场景       |

> - 我认为， ShardingSphere5 模糊了各种策略的界限，除了内置了多种分片策略外，配置上也有较大变动。但官方文档介绍太少，几乎没有，
> - [参考博文](https://blog.csdn.net/qq_34279995/article/details/122421989)

## 分片策略

- 标准策略: 对应 SQL 中的 =、in、Between And 的操作，只支持 单键分片；
    - org.apache.shardingsphere.sharding.route.strategy.type.standard.StandardShardingStrategy
    - 行表达式策略: Groovy 表达式提供 SQL 中的 IN、= 的操作，只支持 单键分片；
        - org.apache.shardingsphere.sharding.algorithm.sharding.complex.ComplexInlineShardingAlgorithm
        - org.apache.shardingsphere.sharding.algorithm.sharding.hint.HintInlineShardingAlgorithm
    - 取模分片算法:
        - MOD 适用于数字类型的分片健，如 Long、Integer
        - HASH_MOD 适用于 字符串 String 类型的分片健，
- 复合策略: 对应 SQL 中的 IN、=、Between And 操作，支持多分片键；
    - org.apache.shardingsphere.sharding.route.strategy.type.complex.ComplexShardingStrategy
- Hint 策略: 通过 Hint 而非 SQL 解析的方式进行分片的操作；
    - org.apache.shardingsphere.sharding.route.strategy.type.hint.HintShardingStrategy
- 不分片策略:
    - org.apache.shardingsphere.sharding.route.strategy.type.none.NoneShardingStrategy

### Standard 策略

> 5.x版本后内置了一些分片算法, 4.x都需要手动实现, 新版本变化很大

#### 行表达式: Inline

> - 根据单一分片健，进行精确分片。默认不支持分片查询，
> - 可以在分库分表策略的属性中配置 allow-range-query-with-inline-sharding: true 开启

- SQL类似于如下，分片键精确分片:
  ```sql
  insert into course values(?,?,?,?);
  select * from course where cid=?;
  select * from course where cid in ?;
  -- 默认 不支持一下范围查询
  select * from course where cid > ?;
  select * from course where cid between (?,?);
  ```
- 分片健: cid 而不是简单的 id，是因为 MyBatis 会对 id 字段默认生成雪花主键；
- 分片算法: m$->{cid%2+1}.course_$->{((cid+1)%4).intdiv(2)+1}
- 真实节点: m$->{1..2}.course_$->{1..2}

#### 时间范围分片算法

```yaml
 rules:
   sharding:
     tables:
       #配置表的规则
       t_order:
         actual-data-nodes: dbsource-0.t_order_2021_$->{['01','02','03','04','05','06','07','08','09','10','11','12']}
         #分表策略
         table-strategy:
           #标准策略
           standard:
             sharding-column: order_time
             sharding-algorithm-name: table-inline
     #分片算法
     sharding-algorithms:
       table-inline:
         type: INTERVAL
         props:
           datetime-pattern: yyyy-MM-dd HH:mm:ss
           datetime-lower: 2021-01-01 00:00:00
           datetime-upper: 2022-01-01 00:00:00
           sharding-suffix-pattern: yyyy_MM
           datetime-interval-amount: 1
           datetime-interval-unit: MONTHS
```

### Complex 策略

### Hint 策略

### 自定义分片策略

根据需求实现不同的类;

- STANDARD ==> StandardShardingAlgorithm
- COMPLEX ==>   ComplexKeysShardingAlgorithm
- HINT ==>   HintShardingAlgorithm

## 广播表

- 所有的数据库，即分片数据源中，都存在的数据表，表结构在各数据源中完全一致。
- 插入、更新操作，需要在所有库节点执行，保持各个分片数据一致。
- 查询操作，可以只从一个节点中获取
- 可以和任何一张表进行 join 操作












