package com.first.family.demo.apachecurator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * apache curator 的使用 Demo
 * 简化 Zookeeper 的 API 操作
 *
 * @description: 简化 Zookeeper 的 API 操作
 * @author: cuiweiman
 * @date: 2022/12/28 17:15
 */
public class ApacheCuratorTest {

    private static CuratorFramework client;

    @BeforeAll
    public static void setUp() {
        String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        client.start();
    }

    @AfterAll
    public static void tearDown() {
        client.close();
    }

    /**
     * 同步方式 znode API 操作
     *
     * @throws Exception exception
     */
    @Test
    public void testSyncOp() throws Exception {
        String path = "/apache_curator";
        byte[] data = {'1'};
        try {
            client.create().withMode(CreateMode.PERSISTENT).forPath(path, data);
            byte[] actualData = client.getData().forPath(path);
            Assertions.assertArrayEquals(actualData, data);
        } finally {
            client.delete().forPath(path);
            client.close();
        }
    }

    /**
     * 异步方式 znode API 操作: 接收异步API的结果回调
     *
     * @throws Exception exception
     */
    @Test
    public void testAsyncCallback() throws Exception {
        String path = "/apache_curator_async";
        final byte[] data = {'2'};
        final CountDownLatch cdl = new CountDownLatch(1);

        // client 客户端配置接收 异步API 执行结果的回调(callback)
        client.getCuratorListenable().addListener((CuratorFramework c, CuratorEvent e) -> {
            switch (e.getType()) {
                case CREATE:
                    System.out.printf("znode %s created\n", e.getPath());
                    // 异步获取节点数据，触发 GET_DATA 回调
                    c.getData().inBackground().forPath(e.getPath());
                    break;
                case GET_DATA:
                    System.out.printf("got the data of znode %s\n", e.getPath());
                    Assertions.assertArrayEquals(e.getData(), data);
                    // 异步删除节点，触发 DELETE 回调
                    c.delete().inBackground().forPath(e.getPath());
                    break;
                case DELETE:
                    System.out.printf("znode %s deleted\n", e.getPath());
                    // TimeUnit.SECONDS.sleep(4);
                    // 线程结束等待，继续执行
                    cdl.countDown();
                    break;
                default:
                    break;
            }
        });
        try {
            // 异步创建节点，触发 CREATE 回调
            client.create().withMode(CreateMode.PERSISTENT).inBackground().forPath(path, data);
            boolean await = cdl.await(3, TimeUnit.SECONDS);
            if (await) {
                System.out.println("CountDownLatch 线程计数为 0");
            } else {
                System.out.println("CountDownLatch#await() 等待时间结束了，但其实线程计数没归0");
            }
        } finally {
            client.close();
        }
    }

    /**
     * 异步方式 znode 的 监听
     *
     * @throws Exception exception
     */
    @Test
    public void testAsyncWatch() throws Exception {
        String path = "/apache_curator_watch";
        final byte[] data = {'3'};
        final byte[] newData = {'4'};
        final CountDownLatch cdl = new CountDownLatch(1);

        // client 客户端配置 接收 znode 的监听事件(watch).
        client.getCuratorListenable().addListener((CuratorFramework c, CuratorEvent e) -> {
            if (CuratorEventType.WATCHED.equals(e.getType())) {
                WatchedEvent watchedEvent = e.getWatchedEvent();
                System.out.printf("watched event: %s \n", watchedEvent);
                if (Watcher.Event.EventType.NodeDataChanged.equals(watchedEvent.getType())
                        && path.equals(watchedEvent.getPath())) {
                    // 监听 指定 path 的 znode 内容变化事件
                    System.out.println("got the event for the triggered watch");
                    byte[] cData = c.getData().forPath(path);
                    Assertions.assertArrayEquals(newData, cData);
                }
                cdl.countDown();
            }
        });
        try {
            client.create().withMode(CreateMode.PERSISTENT).forPath(path, data);
            // 获取数据的同时，添加 watch 监听，监听事件配置在 client 中
            byte[] watchedData = client.getData().watched().forPath(path);
            Assertions.assertArrayEquals(data, watchedData);

            client.setData().forPath(path, newData);
            boolean await = cdl.await(3, TimeUnit.SECONDS);
            if (await) {
                System.out.println("CountDownLatch 线程计数为 0");
            } else {
                System.out.println("CountDownLatch#await() 等待时间结束了，但其实线程计数没归0");
            }
        } finally {
            // 测试结束，删除节点，关闭客户端
            client.delete().inBackground().forPath(path);
            client.close();
        }
    }

    @Test
    public void testAsyncCallbackAndWatch() throws Exception {
        String path = "/async_watch_callback";
        final byte[] data = {'5'};
        final byte[] newData = {'6'};
        final CountDownLatch cdl = new CountDownLatch(2);

        // client 客户端配置接收 异步API 执行结果的回调(callback)，以及 接收 znode 的监听事件(watch).
        client.getCuratorListenable().addListener((CuratorFramework c, CuratorEvent e) -> {
            switch (e.getType()) {
                case CREATE:
                    // 异步 创建 znode  的结果回调
                    System.out.printf("znode %s created\n", e.getPath());
                    byte[] cData = c.getData().watched().forPath(path);
                    Assertions.assertArrayEquals(data, cData);
                    c.setData().forPath(path, newData);
                    cdl.countDown();
                    break;
                case WATCHED:
                    // 监听 znode 事件
                    WatchedEvent watchedEvent = e.getWatchedEvent();
                    System.out.printf("watched event: %s \n", watchedEvent);
                    if (Watcher.Event.EventType.NodeDataChanged.equals(watchedEvent.getType())
                            && path.equals(watchedEvent.getPath())) {
                        // 监听 指定 path 的 znode 内容变化事件
                        System.out.println("got the event for the triggered watch");
                        byte[] cData2 = c.getData().forPath(path);
                        Assertions.assertArrayEquals(newData, cData2);
                    }
                    cdl.countDown();
                    break;
                default:
                    break;
            }
        });
        try {
            client.create().withMode(CreateMode.PERSISTENT).inBackground().forPath(path, data);
            boolean await = cdl.await(5, TimeUnit.SECONDS);
            if (await) {
                System.out.println("CountDownLatch 线程计数为 0");
            } else {
                System.out.println("CountDownLatch#await() 等待时间结束了，但其实线程计数没归0");
            }
        } finally {
            // 测试结束，删除节点，关闭客户端
            client.delete().forPath(path);
            client.close();
        }
    }


}
