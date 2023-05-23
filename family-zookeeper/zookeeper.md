[TOC]

# Zookeeper

## C(一致性)A(可用性)P(分区容错性)

- Consistency: 多个副本之间能够保持一致的特性（严格的一致性）
- Available: 指系统提供的服务必须一直处于可用的状态
- Partition-tolerance: 在遇到任何网络分区故障的时候，仍然能够对外提供满足一致性或可用性的服务，除非整个网络环境都发生了故障

> ZK 遵循 CP 理论: ZK定位于分布式协调服务，在其管辖下的所有服务之间保持同步、一致(Zab算法，CP)，
> 若作Service发现服务，其本身没有正确处理网络分割的问题<当多个zk之间网络出现问题-造成出现多个leader-脑裂>，
> 即在同一个网络分区的节点数达不到zk选取leader的数目，它们就会从zk中断开，同时也不能提供Service发现服务。
>
> ZK在leader选举期间，会暂停对外提供服务（因为zk依赖leader来保证数据一致性)，所以丢失了可用性，保证了一致性。

## ZK简介

- [ZooKeeper实战与源码剖析](https://time.geekbang.org/course/intro/100034201)
- [k8s运行ZK](https://blog.csdn.net/MssGuo/article/details/127773132)

> - Zookeeper 是一个开源的分布式协同服务，将复杂且容易出错的分布式系统服务封装起来，抽象出一个高效可靠的原语集，并以一系列简单的接口提供给用户使用。
> - Zookeeper 适合存储和协同相关的关键数据，不适合存储大量数据(或大量KeyValue)
> - CAP(一致性、可用性、分区容错性)理论满足CP原则。Zookeeper 具有强一致性，节点之间数据同步时不允许写。

---

**应用场景：**

- 配置管理
- DNS服务
- 组成员管理
- 各种分布式锁

---

**数据模型：**  
层次模型(data tree 树形模型)，常见于文件系统。节点称为 znode，每个节点都有一个版本version，从0开始计数。

1. 文件系统的树形结构便于表达数据之间的层次关系；
2. 便于为不同的应用分配独立的命名空间；

---

**Znode分类：**

- 持久性znode：ZK集群宕机、或重启均不会丢失；
- 临时性znode：client宕机或者在指定时间的timeout时间内没有与集群通信，znode消失；
- 持久顺序性znode：具备持久性，znode的名称具备顺序性；
- 临时顺序性znode：具备临时性，且名称具备顺序性。

> ephemeral临时性znode 因此具备 分布式锁 的特性

---

**Quorum集群模式：**  
ZK集群至少需要3个节点，1个Leader节点2个Follower节点。

- Follower只处理读请求，收到写请求时转发给Leader节点。
- Leader可以处理读/写请求。

> 数据一致性: 先到达 Leader 节点的写请求会先被处理，来自给定客户端的请求按照发送顺序处理。


--- 

**Java客户端API介绍**

- [API文档](https://zookeeper.apache.org/doc/current/apidocs/zookeeper-server/index.html)
- [官方示例](https://zookeeper.apache.org/doc/current/javaExample.html)

1. 构造函数 `Zookeeper(connectString, sessionTimeout, watcher)`
    - connectString: ip:port,ip2:port2 逗号分隔的ZK服务
    - sessionTimeout: session timeout 时间
    - watcher: 接收 zk 集群的事件
2. 创建znode: `create(path, data, flags)`
    - path: 路径
    - data: data[] 数据
    - flags: znode类型
3. 根据version实现CAS删除: `delete(path, version)`
    - path: 路径
    - version: 版本需要匹配
4. 判断znode是否存在，并设置监听: `exists(path, watch)`
    - path: 路径
    - watch: 设置监听
5. 获取数据并设置监听: `getData(path, watch)`
6. 根据version实现CAS更新: `setData(path, data, version)`
7. 查询孩子并设置监听: `getChildren(path, watch)`
8. 将当前客户端session连接节点与leader节点进行数据同步: `sync(path)`

> - 读取数据的 API 均可以设置监听
> - **监听事件都只会 触发一次，如需要长久监听需要在触发事件中再次添加监听**
> - version 参数可以为 -1 值，表示无条件执行
> - 所有方法都有同步、异步两个版本。同步方法发送请求后等待server响应；异步请求会进入客户端队列，然后马上返回，之后再通过
    callback 来接收服务端的响应。

9. byte[] getData(String path, boolean watch, Stat stat)  
   同步方法: 如果 watch 为 true，该 znode 状态变化会发送给 Zookeeper 构建时指定的 watcher。
10. void getData(String path,Boolean watch, DataCallback cb, Object ctx)  
    异步方法: cb 是 服务端响应回调, ctx 是 callback 的上下文。
11. void getData(String path, Watcher watcher, DataCallback cb, Object ctx)  
    异步方法: watcher 用于接收 znode 的状态变化
12. Stat setData(String path, byte[] data, int version)  
    同步版本, 若 version 为 -1 ,做无条件更新; 若是非负整数，做 CAS 更新
13. void setData(String path, byte[] data, int version, StatCallback cb, Object ctx)  
    异步更新版本

> watch 机制是由 ZK服务端 主动推送的，避免了客户端不断轮询导致的时间和资源的耗费。


--- 
**查看Zookeeper源码:**

```bash
git clone https://github.com/apache/zookeeper.git
cd zookeeper
git checkout branch-3.5.5
# mac 系统安装 ant 插件
brew install ant
# ant 命令编译项目 
ant eclipse
# 导入 Idea 时，导入 Eclipse 项目
``` 

--- 
**此项目代码中，主要实现的功能有:**

1. 监听 znode 路径存在状态，控制 bash 脚本的执行
2. 分布式队列(FIFO): Zookeeper源码中有实现案例 org.apache.zookeeper.recipes.queue.DistributedQueue. **官方不推荐使用:
   高并发性能效果差**
3. 分布式锁

**注意问题:**
> zk天生不适合做队列，但是还是来看看Curator的实现，学习一下Curator的设计

- zk对于传输数据有一个 1MB 的大小限制。
    - 这就意味着实际中zk节点ZNodes必须设计的很小
    - 但实际中队列通常都存放着数以千计的消息
- 如果有很多大的ZNodes，那会严重拖慢的zk启动过程。
    - 包括zk节点之间的同步过程
    - 如果正要用zk当队列，最好去调整initLimit与syncLimit
- 如果一个ZNode过大，也会导致清理变得困难
    - 也会导致getChildren()方法失败
    - Netflix不得不设计一个特殊的机制来处理这个大体积的nodes
- 如果zk中某个node下有数千子节点，也会严重拖累zk性能
- zk中的数据都会放置在内存中。

--- 

## Apache Curator 简化 ZK 开发

```xml
<!-- https://mvnrepository.com/artifact/org.apache.curator/curator-framework -->
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-framework</artifactId>
    <version>5.4.0</version>
</dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.curator/curator-client -->
<dependency>
<groupId>org.apache.curator</groupId>
<artifactId>curator-client</artifactId>
<version>5.4.0</version>
</dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.curator/curator-recipes -->
<dependency>
<groupId>org.apache.curator</groupId>
<artifactId>curator-recipes</artifactId>
<version>5.4.0</version>
</dependency>
```

- Curator Client: 封装了 Zookeeper 类，管理和 Zookeeper 集群的连接，并提供重连机制。
- Curator Framework: 为所有的 ZK 操作提供重试机制，对外提供了 Fluent 风格的API。
- Curator Recipes: 使用 framework 实现了大量 ZK 协同服务。
- Extensions: 扩展模块。
- 提供 Fluent 风格的 API：
    - client.create().withMode(CreateMode.PERSISTENT).forPath(path, data); // 创建持久节点路径和数据
    - client.create().withMode(CreateMode.PERSISTENT).inBackground().forPath(path, data); // 异步 创建持久节点路径和数据
    - client.getData().watched().forPath(path); // 读取数据并设置监听

# 分布式锁

## ZK方案

**原理：**
zk实现分布式锁的原理其实和redis很像，都是往里面插入对应的值，通过zk的create命令来实现，zk中的值是通过树形结构，类似与文件夹的层级目录一样，如果当前节点存在那么create命令就会执行失败，这种情况就代表其他的线程已经获取到了锁，当前线程通过get
-w /xxx的命令对当前锁进行监听，如果当前锁被其他线程释放，那么当前线程会重新参与竞争锁

- **非公平锁：** 就是通过create创建节点，谁创建成功谁就获得了锁，其他锁对这个节点进行监听，当释放锁的时候，所有线程又来竞争这个锁
    - 但是这种情况会引发 **羊群效应：** 当一个节点被释放的时候所有的线程都会来竞争，浪费性能。
- **公平锁：**
  通过zk的临时有序节点来实现，当前线程创建一个临时顺序节点，然后判断当前节点是不是最小的节点。如果是就获得锁，如果不是那么就监听他的上一个节点，等到释放锁的时候再通知后一个节点。然后重复以上逻辑，这个就是公平锁的实现方案。
    - 这样就可以避免羊群效应，减轻服务器的压力，但是这种情况可能会发生 **幽灵节点** 的产生导致死锁

> **幽灵节点：** 就是客户端发送创建命令之后，zk已经成功创建，但是在响应的时候发生了宕机。此时服务端已经实际创建成功，却无法响应给客户端，导致客户端就不会去释放，就造成了幽灵节点。通过
>
Protection模式能够避免这个问题，这个的本质就是在节点前面加上一个唯一的标识，如uuid，人客户端再次请求的时候会比较这个uuid，如果有就认为创建成功了，使用curator的protection模式原理就是这样的，一下附一张公平锁实现原理图：

## ZK和Redis分布式锁方案对比

分布式系统中通常要考虑CAP的，一致性，可用性和分区容错性，很多场景下是很难同时保证CAP的，这个时候就得做出取舍，分布式锁也是这样的。

**redis分布式锁：**

- 优点：性能高，能保证AP，保证其高可用，
- 缺点：不能保证其一致性。
    - 在redis集群+主从的结构中，数据是通过分片存储的。当一个master节点挂了，slave节点还未同步master节点的数据，会导致master数据丢失。若丢失的数据刚好包含锁数据，就有可能发生并发问题，因此不能保证强一致性。
    - 可以通过 redisson 的 **红锁(RedLock)** 来解决，解决的原理其实就是redis的半数写入机制，问题是这样完全降低了redis的性能，所以一般情况下是不采用的。
    - zk 能保证数据其一致性的原因就是 半数写入机制 加上 leader选举的逻辑实现。

**zk分布式锁：**

- 优点: 能够保证数据一致性，每个节点的创建都会同时写入leader和follower节点，半数以上写入成功才返回。如果leader节点挂了，
  选举的流程会优先选举zxid（事务Id）最大的节点，即选取数据最全的节点，又因为半数写入的机制这样就不会导致丢数据（ZAB协议）。
- 缺点: 性能没有redis高。

## Zookeeper 部署配置

***ZK重要配置项***
> conf/zoo.cfg

- tickTime=2000: 心跳时间,默认2000ms即2s,所有zookeeper的时间都以tickTime的倍数表示，客户端与服务器或服务器与服务器之间维持心跳，每隔1个tickTime时间就会发送一次心跳
- initLimit=10: 初始连接时，从服务器同步到主服务器的最大心跳数，数值为tickTime的倍数
- syncLimit=5: 主从服务器之间请求/应答的最大心跳数，数值为tickTime的倍数
- clientPort: Zookeeper的服务端端口
- dataDir: 必须配置。保存文件快照的目录，若没有设置 dataLogDir，事务日志文件也会保存在此目录
- dataLogDir: 保存事务日志文件的目录。由于ZK再提交一个事务之前，需要保证事务日志记录的落盘，所以需要为 dataLogDir
  分配一个独占的存储目录
- 集群节点配置，server.ID=IP:节点通讯端口:选举端口
  ```
  # ID对应配置在 data/myid 文件中
  server.1=127.0.0.1:2888:3888
  server.2=127.0.0.1:2889:3889
  server.3=127.0.0.1:2890:3890
  ```
- 普罗米修斯监测器: metricsProvider.className=org.apache.zookeeper.metrics.prometheus.PrometheusMetricsProvider
- 监测端口: metricsProvider.httpPort=7000
- 是否导出JVM信息: metricsProvider.exportJvmInfo=true
    - 开启普罗米修斯monitor后，访问 ip:httpPort 即可

---
***单节点硬件要求***

- CPU: Zookeeper对CPU消耗不高，只需保证ZK可以独占一个CPU核即可，所以至少使用一个双核CPU。
- 存储: 存储设备的写延迟会影响事务提交效率，因此建议为 dataLogDir 分配独占的 SSD 盘。
- 内存: ZK需要在内存中存储 data tree，一般的应用场景至少需要 8G 内存。

---
***ZK日志log4j配置***
> conf/log4j.properties

***日志级别补充***

- DEBUG（低）: 详细的信息,通常只出现在诊断问题上
- INFO: 确认一切按预期运行
- WARNING: 表明一些意想不到的事情发生了,或表明一些问题在不久的将来(例如。磁盘空间低”)。软件暂时还能按预期工作
- ERROR: 严重的问题,软件没能执行一些功能
- CRITICAL（高）：严重的错误,这表明程序本身可能无法继续运行

这5个等级，也分别对应5种打日志的方法: debug, info, warning, error, critical。默认 WARNING，在WARNING及以上才被跟踪。

## Zookeeper 集群

> - 查看节点状态: ./zkServer.sh status
> - 客户端查看集群节点配置: [zk: localhost:2181(CONNECTED) 0] config

ZooKeeper采用主从模式,有主节点(Leader)和从节点(Follower),因此一个集群最少需要两台服务器,又因为偶数个节点会导致投票选举时出现平票的情况,
所以集群的个数一般为奇数个即2n+1, 故最小集群个数为3台.

1. 主节点(Leader)的作用:
    - 负责管理整个集群, 保证数据的全局一致性
    - 负责数据事务的相关操作
    - 转发数据非事务操作给从节点

2. 从节点(Follower)的作用:
    - 实时从主节点拉取数据从而保持数据的一致性
    - 负责数据非事务相关的操作
    - 转发数据事务操作给主节点

3. Observer 观察者节点的作用:
    - 和Follower一样,接收leader的inform信息, 更新本地存储.
    - 不参与半数写入机制提交 和 选举投票, 因此可以提升集群的读写性能。
    - 实现跨数据中心的集群部署:
      比如在北京和香港部署ZK集群，要求两地的读请求都低延迟，那么可以将 香港的 节点全部设置成
      Observer。可以将原本是leader和follower节点之间的 propose、ack、commit
      三个跨广域网的消息变成一个inform(通知)跨广域网的消息。

### Observer节点

例如处理一个写请求:

- 集群信息: 节点1,节点2（leader）,节点3
- 客户端与节点1 建立 Session 连接, 并 发起写请求.

---
**Leader和Follower集群写**

1. 节点1(follower) 收到写请求, forward 到 leader节点2;
2. 节点2(leader) 发送 Propose 给其它Follower节点, Follower节点响应 Accept 给 leader;
3. 节点2(leader) 接收到 一半以上 Follower节点的 Accept 后, 向 节点1 响应 Commit;
4. 节点1(follower) 接收到 commit 后，响应给客户端，写请求结束。

---
**Leader、Follower、Observer 集群写**
> 一部分节点是 Observer 节点，可以减少 Leader 和 Follower 之间的交互。

1. 节点1(Observer) 收到写请求, forward 到 leader节点2;
2. 节点2(leader) 发送 Propose 给Follower节点, Follower节点会响应 Accept 给 leader;
3. 同时节点2(leader) 发送 Inform 给 Observer, Observer 节点会同步本地存储;
4. 节点2(leader) 接收到 一半以上 Follower节点的 Accept 后, 向 节点1 响应 Commit;
5. 节点1(Observer) 接收到 commit 后，响应给客户端，写请求结束。

### ZK的动态配置

当集群节点需要调整时，若没有动态配置，那么需要：

1. 停止ZK集群
2. 更改 zoo.cfg 的 server.n项
3. 启动新集群的ZK节点
    1. 停止ZK集群，将造成节点不可用
    2. 重启各个节点，当数据不全的Follower节点被选举为Leader时，将导致数据丢失

**dynamic reconfiguration**
> 动态重构 ZK 集群节点，不需暂停重启服务。
> - [zookeeperReconfig Since 3.5.0](https://zookeeper.apache.org/doc/r3.8.0/zookeeperReconfig.html)
> - [ZooKeeper动态配置](https://www.cnblogs.com/dupang/p/5649843.html)
> - [ZooKeeper动态重新配置](https://blog.csdn.net/Aria_Miazzy/article/details/86609693)

### ZK在本地存储数据

- 内存存储: data tree
- 磁盘存储:
    - 事务日志文件，/dava/version-2 每条事务日志对应一个编号 zxid，zxid关联快照文件
      snapshot。zxid是long64位整数类型，高4个字节(左)存储 epoch ，低4个字节存储 counter
    - acceptedEpoch 文件: quorum集群模式才会生成, standalone 单节点不会生成
    - currentEpoch 文件: quorum集群模式才会生成, standalone 单节点不会生成

- InputArchive: 序列化类
- OutputArchive: 反序列化类

### 本地存储架构

内存数据库结构 + WAL事务日志(transaction log)

```text
|-- TxnLog: 接口类型,提供读写事务日志的API  
|---- FileTxnLog: 基于文件的TxnLog实现  
|-- Snapshot: 快照接口类型,基于序列化、反序列化、访问快照的API  
|---- FileSnap: 基于文件的Snapshot实现  
|---- FileTxnSnapLog: TxnLog和SnapShot的封装。  
|-- DataTree: ZK的内存数据结构,是所有znode构成的树  
|---- DataNode: znode的数据结构
```

# ZK的网络通信

## TCP/IP协议

- 应用层 Http SSH Socket
- 传输层 TCP 有序的面相字节流可靠的传输协议; UDP 无连接不可靠的数据包类型传输协议
- 网络层 IP 将一个IP中的数据传送到目标IP
- 链路层 Ethernet 将数据转化成电信号并传输

**ZK在TCP协议的基础上使用Socket API进行交互**

- Socket: 表示网络中接收和发送数据的一个endpoint,由ip和tcp端口号组成
- connection: 表示两个endpoint之间数据传输的通道, 由两个endpoint的socket组成

**大端模式与小端模式**
TCP是大端模式

- 小端模式: 低位的字节存储在内存的低位上,即低地址存储低字节位;
- 大端模式: 低位的字节存储在内存的高位上,即低地址存放高字节位;

```bash
# ZK RPC 请求包和响应包的结构.
# data的header中, 包含请求的序号,响应头中还包含zk的事务ID即zxid,以及错误码
4字节的len 和 data.

# 请求
Len RequestHeader Request

# 响应
Len ReplyHeader Response

```





# ZK实现协同服务

## 服务发现

> 服务发现至少需要具备三个功能:
> - 服务注册
> - 服务实例的获取
> - 服务变化的通知机制

***curator-x-discovery***

- **Node Cache**: 是 curator 的一个 recipe, 会在本地缓存一个znode的数据, 并长期监控 znode 变化并接收
  create,update,delete 事件通知, 根据通知修改本地缓存的数据。用户可以在 Node Cache 上注册 listener 从而获取到 curator
  在本地缓存中 znode 副本的 更新通知, 达到一个长期监控的效果.
- **Path Cache**: 功能一样, 缓存对象是 一个 znode 目录下所有的子节点.
- **container 节点**: 新引入的 znode. 目的在于下挂子节点, 当 container 子节点被清空之后, container 节点也会被 ZK 删除.
  服务发现中 base path 节点和服务节点 都是 container 类型节点.

> 服务发现源码关键类: ServiceDiscoveryImpl

# 分布式一致算法

## Paxos协议(Chubby使用)

分布式一致性算法: 即一个分布式系统中的各个进程如何就某个值（决议）达成一致。

Paxos算法运行在允许宕机故障的异步系统中，不要求可靠的消息传递，可容忍消息丢失、延迟、乱序以及重复。它利用大多数 (Majority)
机制保证了2F+1的容错能力，即2F+1个节点的系统最多允许F个节点同时出现故障。

一个或多个提议进程 (Proposer) 可以发起提案 (Proposal)
，Paxos算法使所有提案中的某一个提案，在所有进程中达成一致。系统中的多数派同时认可该提案，即达成了一致。最多只针对一个确定的提案达成一致。

Paxos将系统中的角色分为 提议者 (Proposer)，决策者 (Acceptor)，和最终决策学习者 (Learner)
。在多副本状态机中，每个副本同时具有Proposer、Acceptor、Learner三种角色。
:

- Proposer: 提出提案 (Proposal)。Proposal信息包括提案编号 (Proposal ID) 和提议的值 (Value)。
- Acceptor：参与决策，回应Proposers的提案。收到Proposal后可以接受提案，若Proposal获得多数Acceptors的接受，则称该Proposal被批准。
- Learner：不参与决策，从Proposers/Acceptors学习最新达成一致的提案（Value）。

Paxos算法通过一个决议分为两个阶段（Learn阶段之前决议已经形成）：

1. 第一阶段：Prepare阶段。Proposer向Acceptors发出Prepare请求，Acceptors针对收到的Prepare请求进行Promise承诺。
2. 第二阶段：Accept阶段。Proposer收到多数Acceptors承诺的Promise后，向Acceptors发出Propose请求，Acceptors针对收到的Propose请求进行Accept处理。
3. 第三阶段：Learn阶段。Proposer在收到多数Acceptors的Accept之后，标志着本次Accept成功，决议形成，将形成的决议发送给所有Learners。

Paxos算法流程中的每条消息描述如下：

- Prepare: Proposer生成全局唯一且递增的Proposal ID (可使用时间戳加Server ID)
  ，向所有Acceptors发送Prepare请求，这里无需携带提案内容，只携带Proposal ID即可。
- Promise: Acceptors收到Prepare请求后，做出“两个承诺，一个应答”。
  两个承诺：
    1. 不再接受Proposal ID小于等于（注意：这里是<= ）当前请求的Prepare请求。
    2. 不再接受Proposal ID小于（注意：这里是< ）当前请求的Propose请求。

  一个应答：
    1. 不违背以前作出的承诺下，回复已经Accept过的提案中Proposal ID最大的那个提案的Value和Proposal ID，没有则返回空值。


- Propose: Proposer 收到多数Acceptors的Promise应答后，从应答中选择Proposal
  ID最大的提案的Value，作为本次要发起的提案。如果所有应答的提案Value均为空值，则可以自己随意决定提案Value。然后携带当前Proposal
  ID，向所有Acceptors发送Propose请求。
- Accept: Acceptor收到Propose请求后，在不违背自己之前作出的承诺下，接受并持久化当前Proposal ID和提案Value。
- Learn: Proposer收到多数Acceptors的Accept后，决议形成，将形成的决议发送给所有Learners。

## Raft协议

etcd、consul
使用的一致性算法。paxos基本算法解决的是如何保证单一客户端操作的一致性，完成每隔操作需要至少两轮消息交换。Raft有Leader的概念，处理客户端操作之前必须选举Leader，选举至少有一轮消息交换。但选取之后，处理客户端操作只需要一轮消息交换。

## Zab协议(ZK使用)

## Chubby

Chubby是google的分布式锁系统(为分布式锁服务)，GFS和Bigtable都用Chubby进行协同服务，Zookeeper借鉴了Chubby的设计思想。

- Chubby集群成为 cell，由多个 replica 实例组成，其中一个replica是整个cell的master，所有读写请求只能通过master处理。
- 应用引入 Chubby 客户端库来使用Chubby服务。Chubby客户端在和master建立session后，通过发RPC给master来访问Chubby数据。
- 每个客户端维护一个保证数据一致性的cache



# leader选举
> 一个vote由voteId和voteZxid组成。当两个vote比较时，若 A(voteZxid) > B(voteZxid) 或者  A(voteZxid) == B(voteZxid) && A(voteId) > B(mySid) 时, 判定 A节点比B节点新。
> 源码位置: org.apache.zookeeper.server.quorum.QuorumPeerMain.main

1. 一个zk节点A向所有节点发送vote(voteId, voteZxid) 开始选举, voteId是节点自己的ID(mySid), voteZxid 是节点上最新的 zxid(myZxid)。
2. 其它节点B在接收到 vote(voteId,voteZxid)后进行判断，该A节点与当前B节点哪一个新。若A节点新，则当前B节点将 mySid=voteId, myZxid=voteZxid,再把vote发送给所有的ZK节点。否则什么也不做。
3. 保证具有最新 zxid 的节点赢得选举。选举之后follower节点和leader节点状态同步完成之后，才开始处理客户端请求。(此处不满足A理论)
4. 选举过程中，若B节点没有响应来自A节点的请求，那A节点会在timeout之后进行重试，在timeout之前不会处理客户端请求。

# Zab协议
> Zookeeper Atomic Broadcast 协议

1. Leader 发送 proposal 给集群中的所有节点
2. 节点接收到 proposal 之后, 把 proposal 落盘, 并发送 ack 给 leader
3. leader 收到多数节点的 ack 之后, 发送 commit 给集群中的所有节点


# etcd

## 简介

> etcd: etc来源于 unix 的/etc配置文件路径，d表示distributed system

etcd是一个高可用分布式KV系统，可以实现分布式协同服务，采用Raft一致性算法，基于Go语言实现。

**应用场景**

- k8s使用etcd做服务发现和配置信息管理;
- openstack使用etcd做配置管理和分布式锁;
- rook使用etcd研发编排引擎;

etcd和zookeeper覆盖基本一样的协同服务场景，zookeeper需要把所有数据都加载到内存，一般需要存储几百MB的数据。etcd使用bbolt存储引擎，可以处理GB级别的数据。

**数据模型**

KV模型. 所有的Key通过字典顺序排序, 整个etcd的KV存储维护一个递增的64位整数， etcd使用这个整数为每一次KV更新分配一个version.
每个key可以有多个version，创建 Key 生成 CreateRevision,修改 Key 生成 ModRevision, 删除操作会生成一个 tombstone(墓碑) 的新
version。如果etcd进行了 compaction, etcd 会对 compaction revision 之前的key-value进行清理。

**数据存储**

- etcd使用 bbolt 进行KV存储, bbolt 使用持久化的 B+tree 保存 key-value. 三元组(major,sub,type)是B+tree的key,
  major是version,
  sub 用来识别和区分一次更新操作中的key, type 保存可选的特殊值(例如type取值为t代表这个三元组对应的是 tombstone). 如此来加速一个
  version 上的range 查找.

- etcd 还维护一个 in-memory 的 B tree 索引，索引中的 key 即是 key-value 中的key.

## etcd API

etcd使用 gRPC 对外提供API，分为三大类：

- kv: key-value 的创建、更新、读取和删除;
- watch: 提供监控数据更新的机制;
- Lease: 支持客户端的 keep-alive 消息;

**API的响应格式**

- cluster_id: 创建响应的 etcd 集群ID;
- member_id: 创建响应的 etcd 节点ID;
- revision: 创建响应的 etcd kv 的 revision;
- raft_term: 创建响应时的 raft term;

## etcd 集群

> 集群中 2380 端口用于节点之间的通讯, 2370 端口对外提供 gRPC 服务可以供客户端进行API调用. 



