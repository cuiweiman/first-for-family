package com.first.family.demo.apachecurator.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An example leader selector client. Note that {@link LeaderSelectorListenerAdapter} which has the
 * recommended handling for connection state issues
 * <p>
 * 客户端选举 leader 的示例。请注意，LeaderSelectorListenerAdapter 有 解决连接状态问题 的处理建议
 *
 * @description: Apache Curator 实现 选举
 * @author: cuiweiman
 * @date: 2023/2/27 18:46
 */
public class ExampleClient extends LeaderSelectorListenerAdapter implements Closeable {

    private final String name;
    private final LeaderSelector leaderSelector;
    private final AtomicInteger leaderCount = new AtomicInteger();

    public ExampleClient(CuratorFramework client, String path, String name) {
        this.name = name;
        /*
         * create a leader selector using the given path for management,
         * all participants(参与者) in a given leader selection must use the same path
         * ExampleClient here is also a LeaderSelectorListener but this isn't required
         */
        this.leaderSelector = new LeaderSelector(client, path, this);
        this.leaderSelector.autoRequeue();
    }

    /**
     * the selection for this instance doesn't start until the leader selector is started.
     * leader selection is done in the background
     * so this call to {@link LeaderSelector#start()} returns immediately
     */
    public void start() {
        this.leaderSelector.start();
    }

    @Override
    public void close() {
        this.leaderSelector.close();
    }

    /**
     * we are now the leader.
     * This method should not return until we want to relinquish leadership.
     * <p>
     * 赢得选举时会被调用，方法退出后，将表示 放弃了 LeaderShip。
     * 在我们想放弃领导权之前，这种方法不应再出现（被执行）
     *
     * @param client zkClient
     */
    @Override
    public void takeLeadership(CuratorFramework client) {
        final int waitSeconds = (int) (Math.random() * 10 + 1);
        System.out.printf("%s is now the leader, waiting %d seconds... \n", this.name, waitSeconds);
        System.out.printf("%s has been leader, %d time(s) before. \n", this.name, this.leaderCount.getAndIncrement());

        try {
            TimeUnit.SECONDS.sleep(waitSeconds);
        } catch (InterruptedException e) {
            System.err.println(name.concat(" was interrupted."));
            Thread.currentThread().interrupt();
        } finally {
            System.out.printf("%s relinquishing leadership.\n", name);
        }

    }
}
