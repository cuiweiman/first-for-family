package com.first.family.kafka.demo;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.DescribeLogDirsResult;
import org.apache.kafka.clients.admin.ListConsumerGroupOffsetsResult;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @description: 管理员客户端
 * @author: cuiweiman
 * @date: 2023/5/23 10:01
 */
public class AdminClientDemo {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 600000);

        try (AdminClient client = AdminClient.create(props)) {
            // topic 增删查
            delimiter("查看topic列表");
            ListTopicsResult listTopicsResult = client.listTopics();
            listTopicsResult.names().get().forEach(System.out::println);
            // topic 增删查
            delimiter("查看消费者组列表");
            ListConsumerGroupsResult consumerGroupsResult = client.listConsumerGroups();
            consumerGroupsResult.all().get().forEach(System.out::println);

            // offset 修改、删除和查询
            delimiter("消费者组的offset");
            ListConsumerGroupOffsetsResult groupOffsetsResult = client.listConsumerGroupOffsets("test-group");
            groupOffsetsResult.all().get().forEach((k, v) -> System.out.println("key=" + k + "\tvalue=" + v));

            /*Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>(1);
            TopicPartition key = new TopicPartition("test-topic", 0);
            OffsetAndMetadata value = new OffsetAndMetadata(0);
            offsets.put(key, value);
            AlterConsumerGroupOffsetsResult alterConsumerGroupOffsetsResult = client.alterConsumerGroupOffsets("test-group", offsets);
            alterConsumerGroupOffsetsResult.all().whenComplete((Object t, Throwable throwable) -> {
                if (Objects.nonNull(throwable)) {
                    System.err.println(throwable.getMessage());
                } else {
                    System.out.println("offset 重置成功");
                }
            });
            Thread.currentThread().join(1000);*/

            delimiter("集群信息描述");
            DescribeClusterResult describeClusterResult = client.describeCluster();
            System.out.println(describeClusterResult.clusterId().get());
            delimiter("broker 磁盘占用空间比");
            DescribeLogDirsResult describeLogDirsResult = client.describeLogDirs(List.of(0, 1, 2));
            describeLogDirsResult.allDescriptions().get().forEach((k, v) -> System.out.println("k=" + k + ", v=" + v));


        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delimiter(String desc) {
        System.out.println("\t====================\t" + desc + "\t====================");
    }


}
