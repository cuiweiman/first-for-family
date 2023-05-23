package com.first.family.demo;

import com.google.common.collect.ImmutableList;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Op;
import org.apache.zookeeper.OpResult;
import org.apache.zookeeper.Transaction;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * multi api 类似于数据库的事务，一系列操作汇聚在一个方法中，全执行成功或全失败
 *
 * @description: multi api
 * @author: cuiweiman
 * @date: 2023/3/21 13:35
 */
public class MultiApiDemo {

    private final String pathPrefix = "/multi";
    private ZooKeeper zk;
    private CountDownLatch startLatch;
    private CountDownLatch closeLatch;
    private AsyncCallback.MultiCallback callback;

    private final String path1 = pathPrefix + "1";
    private final String path2 = pathPrefix + "2";
    private final byte[] data1 = {0x1, 0x2};
    private final byte[] data2 = {0x9, 0x8};


    @BeforeEach
    public void setUp() throws Exception {
        startLatch = new CountDownLatch(1);
        closeLatch = new CountDownLatch(1);
        callback = (int rc, String path, Object ctx, List<OpResult> opResults) -> {
            Assertions.assertEquals(KeeperException.Code.OK.intValue(), rc);
            System.out.println("delete multi executed");
            closeLatch.countDown();
        };
        String hostPort = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        zk = new ZooKeeper(hostPort, 10000, new DefaultWatcher());
        startLatch.await();
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeLatch.await(5, TimeUnit.SECONDS);
        zk.close();
    }

    @Test
    public void testMulti() throws Exception {
        Op createOp1 = Op.create(path1, data1, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        Op createOp2 = Op.create(path2, data2, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zk.multi(ImmutableList.of(createOp1, createOp2));
        System.out.println("create multi executed");

        Assertions.assertEquals(data1, zk.getData(path1, false, null));
        Assertions.assertEquals(data2, zk.getData(path2, false, null));

        Op delOp1 = Op.delete(path1, -1);
        Op delOp2 = Op.delete(path2, -1);
        zk.multi(ImmutableList.of(delOp1, delOp2), callback, null);
    }

    @Test
    public void testTransaction() throws Exception {
        Transaction tx = zk.transaction();
        tx.create(path1, data1, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        tx.create(path2, data2, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        tx.commit();
        System.out.println("transaction committed");

        Assertions.assertEquals(data1, zk.getData(path1, false, null));
        Assertions.assertEquals(data2, zk.getData(path2, false, null));

        tx = zk.transaction();
        tx.delete(path1, -1);
        tx.delete(path2, -1);
        tx.commit(callback, null);
    }

    class DefaultWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            if (event.getType() == Event.EventType.None
                    && event.getState() == Event.KeeperState.SyncConnected) {
                System.out.println("ZK client connected");
                startLatch.countDown();
            }
        }
    }

}
