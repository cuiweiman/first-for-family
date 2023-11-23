- [深度解读分布式事务Seata入门到实践](https://www.bilibili.com/video/BV1xk4y1i7i3)
- [官网: Seata 是一款开源的分布式事务解决方案，致力于在微服务架构下提供高性能和简单易用的分布式事务服务](https://seata.io/zh-cn/)
- [Seata官方文档](https://seata.io/zh-cn/docs/overview/what-is-seata.html)
- [Seata-1.7.0服务端部署](https://article.juejin.cn/post/7257713854745395255)

# 分布式事务(DTP)

> 分布式事务 Distributed Transaction Processing

- 强一致性协议 : XA 协议，需要数据库支持
- 弱一致性事务(最终一致性) :

## CAP

- C: Consistency 一致性 简单理解为 分布式系统各节点的数据是一致的。
    - 强一致性: 全部节点，或半数以上节点数据都保持一致。
    - 弱一致性: 部分节点数据一致后，通过各种协议逐渐同步数据，达到最终一致。
- A: Availability 可用性
  在某个考察时间，系统能够正常运行的概率或时间占有率期望值。
    - 考察时间为指定瞬间，则称瞬时可用性；
    - 考察时间为指定时段，则称时段可用性；
    - 考察时间为连续使用期间的任一时刻，则称固有可用性。
    - 它是衡量设备在投入使用后实际使用的效能，是设备或系统的可靠性、可维护性和维护支持性的综合特性
- P: Partition tolerance 分区容错性, 能够容忍节点之间的网络通信的故障。

## 分布式事务(DTP)模型

> Distributed Transaction Processing

- **AP** :  Application, 应用程序
- **TM** : Transaction Manager, 事务管理器
    - 如 SpringBoot 的 Transactional
- **RM** : Resource Manager, 资源管理器。
    - 如 MySQL、其他 DB。分布式系统会涉及多个 DB，分布式事务是解决多 DB 事务操作一致性问题。

## 两阶段提交

> 三阶段提交模型比较复杂，不常用

在规范的情况下，基于 DTP 模型设计，分布式事务的完成分为两个阶段

1. Prepare 阶段
2. Commit Rollback 阶段

**成功提交情况**

1. DB1 开启事务执行 SQL1，执行成功，但不提交，将结果交给 TM 事务管理器。
2. DB2 开启事务执行 SQL2，执行成功，但不提交，将结果交给 TM 事务管理器。
3. TM 事务管理器进行判断，两个事务均可以执行成功，再触发 DB1、DB2 提交事务。

**失败回滚情况**

1. DB1 开启事务执行 SQL1，执行失败，但不提交，将结果交给 TM 事务管理器。
2. DB2 开启事务执行 SQL2，执行成功，但不提交，将结果交给 TM 事务管理器。
3. TM 事务管理器进行判断，存在失败情况，对成功的事务进行回滚通知。

# 一致性协议

## XA强一致性协议

> - XA 是 TM事务管理器 和 RM资源管理器 的通讯协议，在 TM 和多个 RM 之间建立通讯。
> - 因此 RM 管理的 DataBase 必须支持 XA 协议，> show engines, InnoDB 引擎支持。

**XA 的语法规范:**

- xa_start
- xa_end
- xa_prepare
- xa_commit
- xa_rollback

### 命令行模拟XA事务

```sql
-- 开启 XA 事务， 操作不同的数据库
xa start 'transactionID';

update db1.tb1
set money=money - 199
where id = 1;

update db2.tb1
set money=money + 199
where id = 9;

xa end 'transactionID';

--  XA 事务 prepare 阶段
xa prepare 'transactionID';

-- XA 事务 提交 阶段
xa commit 'transactionID';

-- XA 事务 回滚 阶段
xa rollback 'transactionID';

```

## Java 模拟 XA 实现

com.first.xa.java.XaDemo1

# Seata 分布式事务工具

- [Seata 是一款开源的分布式事务解决方案，致力于在微服务架构下提供高性能和简单易用的分布式事务服务](https://seata.io/zh-cn/)
- [Seata官方文档](https://seata.io/zh-cn/docs/overview/what-is-seata.html)

1. 支持 强一致性，满足 CP 理论(保证一致性)
    1. XA 模式
2. 支持 弱一致性，满足 AP 理论(保证可用性)
    1. AT模式
    2. TCC 模式
    3. Saga 模式

## Seata 模型

- TC Transaction Coordinator 事务协调者,是一个服务端。
    - 维护全局和分支事务的状态，驱动全局事务提交或回滚。
- TM Transaction Manager 事务管理器
    - 定义全局事务的范围：开始全局事务、提交或回滚全局事务。
- RM Resource Manager 资源管理器
    - 管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚。

> - TM 管理 RM
> - TC 和 TM、RM 之间通过 Netty 实现的 RPC 框架通讯。

## 安装配置 Seata

1. [下载 Seata 1.7.0 Binary 并解压](https://seata.io/zh-cn/blog/download.html)
2. [配置并启动 Seata 服务](https://article.juejin.cn/post/7257713854745395255)
3. seata/conf/application.yml 的一种配置

```yml
server:
  port: 7091

spring:
  application:
    name: seata-server

logging:
  config: classpath:logback-spring.xml
  file:
    path: ${user.home}/logs/seata
  extend:
    logstash-appender:
      destination: 127.0.0.1:4560
    kafka-appender:
      bootstrap-servers: 127.0.0.1:9092
      topic: logback_to_logstash

console:
  user:
    username: seata
    password: seata
seata:
  config:
    # support: nacos, consul, apollo, zk, etcd3
    type: nacos
    nacos:
      server-addr: 172.18.66.31:8848
      # Nacos 中 namespace 命名空间的 ID
      namespace: 418f7bc2-28e5-456c-8538-20b72f111264
      # 
      group: SEATA_GROUP
      username: nacos
      password: nacos
      # Nacos 中配置的文件名称
      data-id: seataServer.properties

  registry:
    # support: nacos, eureka, redis, zk, consul, etcd3, sofa
    type: nacos
    nacos:
      application: seata-server
      server-addr: 127.0.0.1:8848
      group: SEATA_GROUP
      namespace: seata
      cluster: seata-server
      username: nacos
      password: nacos

  #  server:
  #    service-port: 8091 #If not configured, the default is '${server.port} + 1000'
  security:
    secretKey: SeataSecretKey0c382ef121d778043159209298fd40bf3850a017
    max-commit-retry-timeout: -1
    max-rollback-retry-timeout: -1
    rollback-retry-timeout-unlock-enable: false
    enable-check-auth: true
    enable-parallel-request-handle: true
    retry-dead-threshold: 130000
    xaer-nota-retry-timeout: 60000
    enableParallelRequestHandle: true
    recovery:
      committing-retry-period: 1000
      async-committing-retry-period: 1000
      rollbacking-retry-period: 1000
      timeout-retry-period: 1000
    undo:
      log-save-days: 7
      log-delete-period: 86400000
    tokenValidityInMilliseconds: 1800000
    session:
      branch-async-queue-size: 5000 #branch async remove queue size
      enable-branch-async-remove: false #enable to asynchronous remove branchSession
    ignore:
      urls: /,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.jpeg,/**/*.ico,/api/v1/auth/login
```

4. Nacos中seataServer.properties的简单配置

```properties
# 只能使用 驼峰，不能使用 短横线 连接
store.mode=db
store.lock.mode=db
store.session.mode=db
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.cj.jdbc.Driver
store.db.url=jdbc:mysql://127.0.0.1:3306/seata?useSSL=false&useUnicode=true&rewriteBatchedStatements=true&serverTimezone=Asia/Shanghai
store.db.user=root
store.db.password=Baidu@123
store.db.minConn=10
store.db.maxConn=200
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.distributedLockTable=distributed_lock
store.db.queryLimit=1000
store.db.lockTable=lock_table
store.db.maxWait=5000
```

## 接入 Seata 服务

- java原生 API Demo: com.first.xa.java.XaDemo1
- MyBatis原生 API Demo: com.first.seata.mybatis
- SpringBoot 自动注入 Demo: com.first.MySeataApplication

## Seata的 XA 模式原理

![SeataXA](https://img.alicdn.com/tfs/TB1hSpccIVl614jSZKPXXaGjpXa-1330-924.png)

1. seata服务端，即 TC Transaction Coordinator 事务协调者 正常运行
2. 启动 Seata 客户端项目，自动初始化 TM 事务管理器 和 RM 资源管理器，将 TM 和 RM 注册(Netty通讯)到 TC。
3. 执行@GlobalTransactional注解方法，TM 向 TC 发送 Netty 请求注册全局事务，TC 收到后生成唯一的全局事务 ID，响应给 TM，TM 存储在 ThreadLocal 类型变量中。
4. RM(1) 从 ThreadLocal 中获取全局事务 ID 后向 TC 注册分支事务。TC 根据全局事务 ID 将 RM(1) 的分支事务关联到全局事务中的分支子事务，并响应 分支子事务 ID 给 RM(1) 。
5. RM(1) 注册分支事务成功后，开启全局事务(xa start)—>执行业务逻辑代码—>结束全局事务(xa end)、xa prepare，向 TC 通讯事务情况。
6. RM(2) 从 ThreadLocal 中获取全局事务 ID，向 TC 注册分支事务，TC 将分支事务加入到全局事务中，并返回分支子事务 ID 给 RM(2)。
7. RM(2) 注册分支事务成功后，开启全局事务(xa start)—>执行业务逻辑代码—>结束全局事务(xa end)、xa prepare，向 TC 通讯事务情况。
8. 如果 RM(1) 和 RM(2) 都执行成功，那么 TM 通知 TC，做全局事务提交；TC 收到通知后，再通知 RM1、RM2 分别做事务提交。
9. 如果有分支子事务存在失败情况，那么 TM 先通知到 TC 需要回滚，TC 再通知到各个资源管理器 RM，资环管理器控制子分支事务进行事务回滚。












