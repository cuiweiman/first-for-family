package com.first.family.demo.distributedqueue;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper 源码中的 分布式队列实现, 具体原理思路如下:
 * 1. 使用路径为 /queue 的znode为根节点，其下的所有 顺序持久化 子节点 表示队列中的元素.
 * 2. 这些znode名字的后缀数字对应元素在队列中的位置。
 * 3. znode名字后缀数字越小，对应队列元素位置越靠前，即最先进入队列。
 *
 * @description: {@link org.apache.zookeeper.recipes.queue.DistributedQueue}
 * @author: cuiweiman
 * @date: 2022/12/27 17:21
 */
public class DistributedQueue {
    private static final Logger LOG = LoggerFactory.getLogger(DistributedQueue.class);
    private final String dir;
    private ZooKeeper zookeeper;
    private List<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;
    private final String prefix = "qn-";

    public DistributedQueue(ZooKeeper zookeeper, String dir, List<ACL> acl) {
        this.dir = dir;
        if (acl != null) {
            this.acl = acl;
        }
        this.zookeeper = zookeeper;
    }

    /**
     * 返回 Map 类型的 所有子节点，根据 znode id 排序
     * 利用 {@link TreeMap} 进行排序，默认 自然顺序(由小到大)排序
     *
     * @param watcher optional watcher on getChildren() operation.
     * @return map from id to child name for all children
     */
    private Map<Long, String> orderedChildren(Watcher watcher) throws KeeperException, InterruptedException {
        Map<Long, String> orderedChildren = new TreeMap<>();
        List<String> childNames = zookeeper.getChildren(dir, watcher);
        for (String childName : childNames) {
            try {
                //Check format
                if (!childName.regionMatches(0, prefix, 0, prefix.length())) {
                    LOG.warn("Found child node with improper name: {}", childName);
                    continue;
                }
                // 获取子节点的后缀，持久性顺序节点的后缀是递增的数字
                String suffix = childName.substring(prefix.length());
                Long childId = Long.parseLong(suffix);
                orderedChildren.put(childId, childName);
            } catch (NumberFormatException e) {
                LOG.warn("Found child node with improper format : {}", childName, e);
            }
        }
        return orderedChildren;
    }

    /**
     * 查找 child node 后缀数字最小的 节点，并返回其名称
     *
     * @return The name of the smallest child node.
     */
    private String smallestChildName() throws KeeperException, InterruptedException {
        long minId = Long.MAX_VALUE;
        String minName = "";
        List<String> childNames;
        try {
            childNames = zookeeper.getChildren(dir, false);
        } catch (KeeperException.NoNodeException e) {
            LOG.warn("Unexpected exception", e);
            return null;
        }
        for (String childName : childNames) {
            try {
                //Check format
                if (!childName.regionMatches(0, prefix, 0, prefix.length())) {
                    LOG.warn("Found child node with improper name: {}", childName);
                    continue;
                }
                String suffix = childName.substring(prefix.length());
                long childId = Long.parseLong(suffix);
                if (childId < minId) {
                    minId = childId;
                    minName = childName;
                }
            } catch (NumberFormatException e) {
                LOG.warn("Found child node with improper format : {}", childName, e);
            }
        }
        if (minId < Long.MAX_VALUE) {
            return minName;
        } else {
            return null;
        }
    }


    /**
     * 返回 队头元素 (FIFO)，且不修改队列。若队列为空则抛出异常
     * <p>
     * {@link #element()}、{@link #take()}、{@link #remove()} 方法都遵循以下模式:
     * 1. 我们希望返回具有最小序列号的子节点;
     * 2. 由于其他客户端同时 remove() 和 take() 节点, 在我们检查时, 排序中序号最小的孩子节点可能已经不在了;
     * 3. 此时我们会按照顺序继续尝试获取下一个排序最小的孩子节点, 而不是重新调用 {@link ZooKeeper#getChildren(String, boolean)}方法
     *
     * @return the data at the head of the queue.
     * @throws NoSuchElementException 空队列异常
     * @throws KeeperException        ZK服务器返回非零错误代码的错误信号
     * @throws InterruptedException   ZK服务器事务中断
     */
    public byte[] element() throws NoSuchElementException, KeeperException, InterruptedException {
        Map<Long, String> orderedChildren;
        while (true) {
            try {
                orderedChildren = orderedChildren(null);
            } catch (KeeperException.NoNodeException e) {
                throw new NoSuchElementException();
            }
            if (orderedChildren.size() == 0) {
                throw new NoSuchElementException();
            }
            for (String headNode : orderedChildren.values()) {
                if (headNode != null) {
                    try {
                        return zookeeper.getData(dir + "/" + headNode, false, null);
                    } catch (KeeperException.NoNodeException e) {
                        // 若另一个客户端首先删除了节点，需要重试
                    }
                }
            }
        }
    }


    /**
     * 尝试 返回并移除 队头元素，若队列为空则抛出异常
     * 同 {@link #element()} 方法，需要重构
     *
     * @return The former head of the queue
     * @throws NoSuchElementException 空队列异常
     * @throws KeeperException        ZK服务器返回非零错误代码的错误信号
     * @throws InterruptedException   ZK服务器事务中断异常
     */
    public byte[] remove() throws NoSuchElementException, KeeperException, InterruptedException {
        Map<Long, String> orderedChildren;
        while (true) {
            try {
                orderedChildren = orderedChildren(null);
            } catch (KeeperException.NoNodeException e) {
                throw new NoSuchElementException();
            }
            if (orderedChildren.size() == 0) {
                throw new NoSuchElementException();
            }
            for (String headNode : orderedChildren.values()) {
                String path = dir + "/" + headNode;
                try {
                    byte[] data = zookeeper.getData(path, false, null);
                    zookeeper.delete(path, -1);
                    return data;
                } catch (KeeperException.NoNodeException e) {
                    // Another client deleted the node first.
                }
            }
        }
    }

    private static class LatchChildWatcher implements Watcher {
        CountDownLatch latch;

        public LatchChildWatcher() {
            latch = new CountDownLatch(1);
        }

        @Override
        public void process(WatchedEvent event) {
            LOG.debug("Watcher fired: {}", event);
            latch.countDown();
        }

        public void await() throws InterruptedException {
            latch.await();
        }
    }


    /**
     * 返回并移除 队头元素，若队列为空则无限阻塞，直到成功获取到队列元素
     * 类同 {@link #element()} 方法，需要重构
     *
     * @return The former head of the queue
     * @throws NoSuchElementException 空队列异常
     * @throws KeeperException        ZK服务器返回非零错误代码的错误信号
     * @throws InterruptedException   ZK服务器事务中断异常
     */
    public byte[] take() throws KeeperException, InterruptedException {
        Map<Long, String> orderedChildren;
        while (true) {
            LatchChildWatcher childWatcher = new LatchChildWatcher();
            try {
                orderedChildren = orderedChildren(childWatcher);
            } catch (KeeperException.NoNodeException e) {
                zookeeper.create(dir, new byte[0], acl, CreateMode.PERSISTENT);
                continue;
            }
            if (orderedChildren.size() == 0) {
                childWatcher.await();
                continue;
            }
            for (String headNode : orderedChildren.values()) {
                String path = dir + "/" + headNode;
                try {
                    byte[] data = zookeeper.getData(path, false, null);
                    zookeeper.delete(path, -1);
                    return data;
                } catch (KeeperException.NoNodeException e) {
                    // Another client deleted the node first.
                }
            }
        }
    }

    /**
     * 元素 入队
     *
     * @param data data
     * @return true if data was successfully added
     */
    public boolean offer(byte[] data) throws KeeperException, InterruptedException {
        for (; ; ) {
            try {
                zookeeper.create(dir + "/" + prefix, data, acl, CreateMode.PERSISTENT_SEQUENTIAL);
                return true;
            } catch (KeeperException.NoNodeException e) {
                zookeeper.create(dir, new byte[0], acl, CreateMode.PERSISTENT);
            }
        }

    }

    /**
     * 返回 队头元素 (FIFO)，且不修改队列。若队列为空则返回 null
     *
     * @return data at the first element of the queue, or null.
     * @throws KeeperException      ZK服务器返回非零错误代码的错误信号
     * @throws InterruptedException ZK服务器事务中断异常
     */
    public byte[] peek() throws KeeperException, InterruptedException {
        try {
            return element();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * 尝试 返回并移除 队头元素(FIFO), 若队列为空则返回 null
     *
     * @return Head of the queue or null.
     * @throws KeeperException      ZK服务器返回非零错误代码的错误信号
     * @throws InterruptedException ZK服务器事务中断异常
     */
    public byte[] poll() throws KeeperException, InterruptedException {
        try {
            return remove();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
