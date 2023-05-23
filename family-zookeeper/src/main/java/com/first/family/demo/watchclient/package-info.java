/**
 * Demo: 使用 Zookeeper 实现一个监听Demo。当path路径节点存在时，执行 seq.sh 脚本；当path路径节点被删除时，停止执行该脚本；而当path节点修改时，重新执行该脚本。
 *
 * 1. 命令行 启动 Executor，并注入启动参数: 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183 /watch data/znode-data seq.sh
 * 分别表示 zookeeper 服务器的集群地址;需要监听的节点;znode 节点数据写入的路径文件;通过znode节点是否存在控制该脚本是否运行;
 * 2. Executor 构造方法中，添加了Zookeeper的节点状态监听方法 {@link com.first.family.demo.watchclient.Executor#process(org.apache.zookeeper.WatchedEvent)}
 * 当 Zookeeper 的节点状态发生变化时，会执行该方法的逻辑。
 * 3. Executor 构造方法中，添加了自定义的 DataMonitor 实现类，监听 znode 节点是否存在，并设置回调方法 {@link com.first.family.demo.watchclient.DataMonitor#processResult(int, java.lang.String, java.lang.Object, org.apache.zookeeper.data.Stat)}，
 * 从而保证节点状态变化时可以被调用。
 * <p>
 * 4. Executor 线程中不断监听 DataMonitor#dead 属性的内容，若dead=true，则Executor线程不再wait()，线程执行结束，将导致所有的监听方法结束；否则一直wait()等待。
 * 5. Executor的实现类 {@link com.first.family.demo.watchclient.Executor#closing(int)} 被执行，则唤醒所有 wait() 等待线程，导致线程执行结束。
 * 6. Executor的实现类 {@link com.first.family.demo.watchclient.Executor#exits(byte[])} 方法，控制 脚本进程的执行和结束
 */
package com.first.family.demo.watchclient;