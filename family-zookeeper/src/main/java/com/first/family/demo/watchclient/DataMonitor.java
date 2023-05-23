package com.first.family.demo.watchclient;


import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;
import java.util.Objects;


/**
 * 1. 实现 {@link AsyncCallback.StatCallback} 接口，使用 Zookeeper的异步API 时，接收服务端的异步响应
 * 2. 定义实现了 znode 节点状态监听方法
 *
 * @description:
 * @author: cuiweiman
 * @date: 2022/11/16 16:26
 */
public class DataMonitor implements AsyncCallback.StatCallback {

    private ZooKeeper zk;
    /**
     * 需要监听的 节点路径
     */
    private String znode;
    boolean dead;
    /**
     * 自定义的 监听器
     */
    private DataMonitorListener listener;
    private byte[] prevData;


    public DataMonitor(ZooKeeper zk, String znode, DataMonitorListener listener) {
        this.zk = zk;
        this.znode = znode;
        this.listener = listener;
        // 监听当前 zNode 节点, 设置回调
        zk.exists(znode, true, this, null);
    }

    public void handle(WatchedEvent event) {
        String path = event.getPath();
        if (Watcher.Event.EventType.None.equals(event.getType())) {
            // zkClient 和 zkServer 的 session 连接状态 有改变
            switch (event.getState()) {
                case SyncConnected:
                    // SyncConnected 表示未过期
                    break;
                case Expired:
                    // Session 过期
                    dead = true;
                    listener.closing(KeeperException.Code.SESSIONEXPIRED.intValue());
                    break;
                default:
                    break;
            }
        } else {
            // 事件的节点路径不为空，且是我们关心&监听的znode节点
            if (Objects.nonNull(path) && path.equals(znode)) {
                // 当前 zNode 发生变化，添加 watch，通知回调会执行 processResult() 方法
                zk.exists(znode, true, this, null);
            }
        }
    }

    /**
     * watch 的 异步回调 处理方法
     *
     * @param rc   zookeeper 服务端响应code
     * @param path path
     * @param ctx  上下文
     * @param stat zNode信息详情
     */
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        boolean exists;
        KeeperException.Code code = KeeperException.Code.get(rc);
        switch (code) {
            case OK:
                exists = true;
                break;
            case NONODE:
                exists = false;
                break;
            case SESSIONEXPIRED:
            case NOAUTH:
                dead = true;
                listener.closing(rc);
                return;
            default:
                zk.exists(path, true, this, null);
                return;
        }
        byte[] b = null;
        if (exists) {
            try {
                // 获取 znode 中的数据
                b = zk.getData(znode, false, null);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }
        // zNode (数据为空 && 和旧数据不同) || (数据不为空，且和旧数据不同) 时，才判定 节点存在
        boolean flag = (b == null && b != prevData) || (b != null && !Arrays.equals(prevData, b));
        if (flag) {
            listener.exits(b);
            prevData = b;
        }
    }

    public interface DataMonitorListener {
        /**
         * the existence status of the node has changed.
         *
         * @param data data
         */
        void exits(byte[] data);

        /**
         * the zookeeper session is no longer valid
         *
         * @param rc the zookeeper reason code
         */
        void closing(int rc);
    }
}
