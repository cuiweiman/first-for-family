package com.first.family.kafka.demo;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: consumer
 * @author: cuiweiman
 * @date: 2023/5/17 15:18
 */
public class ConsumerDemo3 {
    private static KafkaConsumer<String, String> kafkaConsumer;

    public static void main(String[] args) {
        kafkaConsumer = initConsumer();
        String topic = "iot_data_topic";
        kafkaConsumer.subscribe(List.of(topic));
        consumer();
    }

    private static final int workerNum = 3;
    private static final long keepAliveTime = 0;

    private static final ExecutorService executors = new ThreadPoolExecutor(workerNum, workerNum, keepAliveTime, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void consumer() {
        long begin = Instant.now().getEpochSecond();
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                executors.submit(() -> msgHandler(record));
            }
            long end = Instant.now().getEpochSecond();
            if (begin - end >= 10) {
                break;
            }
        }
    }

    private static void msgHandler(ConsumerRecord<String, String> record) {
        long offset = record.offset();
        String topic = record.topic();
        int partition = record.partition();
        String key = record.key();
        String value = record.value();
        String out = String.format("offset: %d, topic: %s, partition %d, %s : %s", offset, topic, partition, key, value);
        System.out.println(out);
        kafkaConsumer.commitSync();
    }

    private static KafkaConsumer<String, String> initConsumer() {
        String bootstrapServers = "211.140.107.236:9093";
        String groupId = "MyTest";
        String password = "QAZqwe@2022";
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        // 手动提交offset - 关闭
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, "40000");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "20000");

        //ssl加密和认证配置
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        properties.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, "JKS");
        properties.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, "JKS");
        //获取Resources配置文件中client.keystore.jks
        properties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG,
                "/Users/cuiweiman/Downloads/kafka-key/server.keystore.jks");
        properties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, password);
        //设置为空字符串来禁用服务器主机名验证
        properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        properties.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, password);
        //获取Resources配置文件中client.truststore.jks
        properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,
                "/Users/cuiweiman/Downloads/kafka-key/server.truststore.jks");
        properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, password);
        return new KafkaConsumer<>(properties);
    }
}





