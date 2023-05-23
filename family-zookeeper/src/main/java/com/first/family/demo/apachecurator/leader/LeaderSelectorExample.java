package com.first.family.demo.apachecurator.leader;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.assertj.core.util.Lists;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @description: 选举测试
 * @author: cuiweiman
 * @date: 2023/2/27 19:18
 */
public class LeaderSelectorExample {
    private static final int CLIENT_QTY = 10;
    private static final String PATH = "/examples/leader";

    private static final String CONNECT_STRING = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static final RetryPolicy RETRY_POLICY = new ExponentialBackoffRetry(1000, 3);

    public static void main(String[] args) throws Exception {
        System.out.printf("Create %d clients, have each negotiate for leadership " +
                "and then wait a random number of seconds", CLIENT_QTY);
        System.out.println("Notice that leader selection is fair: " +
                "all clients will become leader and will do so the same ");

        List<CuratorFramework> clientList = Lists.newArrayList();
        List<ExampleClient> exampleList = Lists.newArrayList();

        try {
            for (int i = 0; i < CLIENT_QTY; i++) {
                CuratorFramework client = CuratorFrameworkFactory.newClient(CONNECT_STRING, RETRY_POLICY);
                clientList.add(client);
                ExampleClient example = new ExampleClient(client, PATH, "Client # " + i);
                exampleList.add(example);
                client.start();
                example.start();
            }
            System.out.println("Press enter/return to quit");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } finally {
            System.out.println("shutting down...");
            for (ExampleClient exampleClient : exampleList) {
                exampleClient.close();
            }
        }

    }
}
