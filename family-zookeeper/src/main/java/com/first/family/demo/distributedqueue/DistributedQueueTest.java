package com.first.family.demo.distributedqueue;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.NoSuchElementException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @description: Zookeeper 源码分布式队列的单元测试
 * @author: cuiweiman
 * @date: 2022/12/27 18:07
 */
public class DistributedQueueTest implements Watcher {

    protected static final Logger LOG = LoggerFactory.getLogger(DistributedQueueTest.class);

    protected String hostPort = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    public static int CONNECTION_TIMEOUT = 30000;

    @Test
    public void testOffer1() throws Exception {
        String dir = "/testOffer1";
        String testString = "Hello World";
        final int numClients = 1;
        ZooKeeper[] clients = new ZooKeeper[numClients];
        DistributedQueue[] queueHandles = new DistributedQueue[numClients];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = createClient();
            queueHandles[i] = new DistributedQueue(clients[i], dir, null);
        }
        queueHandles[0].offer(testString.getBytes(UTF_8));
        byte[] dequeuedBytes = queueHandles[0].remove();
        assertEquals(new String(dequeuedBytes, UTF_8), testString);
    }


    @Test
    public void testOffer2() throws Exception {
        String dir = "/testOffer1";
        String testString = "Hello World";
        final int numClients = 2;
        ZooKeeper[] clients = new ZooKeeper[numClients];
        DistributedQueue[] queueHandles = new DistributedQueue[numClients];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = createClient();
            queueHandles[i] = new DistributedQueue(clients[i], dir, null);
        }
        // 队列[0] 放入元素
        queueHandles[0].offer(testString.getBytes(UTF_8));
        // 队列[1] 移除并获取元素
        byte[] dequeuedBytes = queueHandles[1].remove();
        // 元素内容一致: 多个队列操作的是同一个 znode 根节点
        assertEquals(new String(dequeuedBytes, UTF_8), testString);
    }

    @Test
    public void testTake1() throws Exception {
        String dir = "/testTake1";
        String testString = "Hello World";
        final int numClients = 1;
        ZooKeeper[] clients = new ZooKeeper[numClients];
        DistributedQueue[] queueHandles = new DistributedQueue[numClients];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = createClient();
            queueHandles[i] = new DistributedQueue(clients[i], dir, null);
        }
        queueHandles[0].offer(testString.getBytes(UTF_8));
        byte[] dequeuedBytes = queueHandles[0].take();
        assertEquals(new String(dequeuedBytes, UTF_8), testString);
    }

    @Test
    public void testRemove1() throws Exception {
        String dir = "/testRemove1";
        final int numClients = 1;
        ZooKeeper[] clients = new ZooKeeper[numClients];
        DistributedQueue[] queueHandles = new DistributedQueue[numClients];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = createClient();
            queueHandles[i] = new DistributedQueue(clients[i], dir, null);
        }
        try {
            queueHandles[0].remove();
        } catch (NoSuchElementException e) {
            return;
        }
        fail();
    }


    public void createNremoveMtest(String dir, int n, int m) throws Exception {
        String testString = "Hello World";
        final int numClients = 2;
        ZooKeeper[] clients = new ZooKeeper[numClients];
        DistributedQueue[] queueHandles = new DistributedQueue[numClients];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = createClient();
            queueHandles[i] = new DistributedQueue(clients[i], dir, null);
        }

        for (int i = 0; i < n; i++) {
            String offerString = testString + i;
            queueHandles[0].offer(offerString.getBytes(UTF_8));
        }

        byte[] data = null;
        for (int i = 0; i < m; i++) {
            data = queueHandles[1].remove();
        }

        assertNotNull(data);
        assertEquals(new String(data, UTF_8), testString + (m - 1));
    }

    @Test
    public void testRemove2() throws Exception {
        createNremoveMtest("/testRemove2", 10, 2);
    }

    @Test
    public void testRemove3() throws Exception {
        createNremoveMtest("/testRemove3", 1000, 1000);
    }

    public void createNremoveMelementTest(String dir, int n, int m) throws Exception {
        String testString = "Hello World";
        final int numClients = 2;
        ZooKeeper[] clients = new ZooKeeper[numClients];
        DistributedQueue[] queueHandles = new DistributedQueue[numClients];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = createClient();
            queueHandles[i] = new DistributedQueue(clients[i], dir, null);
        }

        for (int i = 0; i < n; i++) {
            String offerString = testString + i;
            queueHandles[0].offer(offerString.getBytes(UTF_8));
        }

        for (int i = 0; i < m; i++) {
            queueHandles[1].remove();
        }
        assertEquals(new String(queueHandles[1].element(), UTF_8), testString + m);
    }

    @Test
    public void testElement1() throws Exception {
        createNremoveMelementTest("/testElement1", 1, 0);
    }

    @Test
    public void testElement2() throws Exception {
        createNremoveMelementTest("/testElement2", 10, 2);
    }

    @Test
    public void testElement3() throws Exception {
        createNremoveMelementTest("/testElement3", 1000, 500);
    }

    @Test
    public void testElement4() throws Exception {
        createNremoveMelementTest("/testElement4", 1000, 1000 - 1);
    }

    @Test
    public void testTakeWait1() throws Exception {
        String dir = "/testTakeWait1";
        final String testString = "Hello World";
        final int numClients = 1;
        final ZooKeeper[] clients = new ZooKeeper[numClients];
        final DistributedQueue[] queueHandles = new DistributedQueue[numClients];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = createClient();
            queueHandles[i] = new DistributedQueue(clients[i], dir, null);
        }

        final byte[][] takeResult = new byte[1][];
        Thread takeThread = new Thread(() -> {
            try {
                takeResult[0] = queueHandles[0].take();
            } catch (KeeperException | InterruptedException ignore) {
                // no op
            }
        });
        takeThread.start();

        Thread.sleep(1000);
        Thread offerThread = new Thread(() -> {
            try {
                queueHandles[0].offer(testString.getBytes(UTF_8));
            } catch (KeeperException | InterruptedException ignore) {
                // no op
            }
        });
        offerThread.start();
        offerThread.join();

        takeThread.join();

        assertNotNull(takeResult[0]);
        assertEquals(new String(takeResult[0], UTF_8), testString);
    }

    @Test
    public void testTakeWait2() throws Exception {
        String dir = "/testTakeWait2";
        final String testString = "Hello World";
        final int numClients = 1;
        final ZooKeeper[] clients = new ZooKeeper[numClients];
        final DistributedQueue[] queueHandles = new DistributedQueue[numClients];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = createClient();
            queueHandles[i] = new DistributedQueue(clients[i], dir, null);
        }
        int numAttempts = 2;
        for (int i = 0; i < numAttempts; i++) {
            final byte[][] takeResult = new byte[1][];
            final String threadTestString = testString + i;
            Thread takeThread = new Thread(() -> {
                try {
                    takeResult[0] = queueHandles[0].take();
                } catch (KeeperException | InterruptedException ignore) {
                    // no op
                }
            });
            takeThread.start();
            Thread.sleep(1000);
            Thread offerThread = new Thread(() -> {
                try {
                    queueHandles[0].offer(threadTestString.getBytes(UTF_8));
                } catch (KeeperException | InterruptedException ignore) {
                    // no op
                }
            });
            offerThread.start();
            offerThread.join();
            takeThread.join();
            assertNotNull(takeResult[0]);
            assertEquals(new String(takeResult[0], UTF_8), threadTestString);
        }
    }


    protected ZooKeeper createClient() throws IOException {
        return new ZooKeeper(hostPort, CONNECTION_TIMEOUT, this);
    }

    @Override
    public void process(WatchedEvent event) {
        LOG.warn("eventType: {}, path: {}, state: {}", event.getType(), event.getPath(), event.getState());
    }
}
