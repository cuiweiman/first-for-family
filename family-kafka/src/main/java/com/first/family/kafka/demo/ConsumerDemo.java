package com.first.family.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
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
 * {@link KafkaConsumer#poll(Duration)}和{@link KafkaConsumer#commitAsync()} 在不同的
 * 线程里执行，会触发线程安全异常
 * <pre class="code">
 * Exception in thread "main" java.util.ConcurrentModificationException: KafkaConsumer is not safe for multi-threaded access
 * 	at org.apache.kafka.clients.consumer.KafkaConsumer.acquire(KafkaConsumer.java:2491)
 * 	at org.apache.kafka.clients.consumer.KafkaConsumer.acquireAndEnsureOpen(KafkaConsumer.java:2475)
 * 	at org.apache.kafka.clients.consumer.KafkaConsumer.poll(KafkaConsumer.java:1223)
 * 	at org.apache.kafka.clients.consumer.KafkaConsumer.poll(KafkaConsumer.java:1216)
 * 	at com.first.family.kafka.demo.ConsumerDemo.consumer(ConsumerDemo.java:41)
 * 	at com.first.family.kafka.FamilyKafkaApp.main(FamilyKafkaApp.java:25)
 * </pre>
 *
 * @description: consumer
 * @author: cuiweiman
 * @date: 2023/5/17 15:18
 */
public class ConsumerDemo {
    private final KafkaConsumer<String, String> kafkaConsumer;

    public ConsumerDemo(List<String> topicList) {
        this.kafkaConsumer = initConsumer();
        kafkaConsumer.subscribe(topicList);
    }

    private final int workerNum = 3;
    private final long keepAliveTime = 0;

    private final ExecutorService executors = new ThreadPoolExecutor(workerNum, workerNum, keepAliveTime, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

    public void consumer() {
        long begin = Instant.now().getEpochSecond();
        while (true) {
            ConsumerRecords<String, String> records = this.kafkaConsumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                executors.submit(() -> msgHandler(record));
            }
            long end = Instant.now().getEpochSecond();
            if (begin - end >= 10) {
                break;
            }
        }
    }

    private void msgHandler(ConsumerRecord<String, String> record) {
        long offset = record.offset();
        String topic = record.topic();
        int partition = record.partition();
        String key = record.key();
        String value = record.value();
        String out = String.format("offset: %d, topic: %s, partition %d, %s : %s", offset, topic, partition, key, value);
        System.out.println(out);
        this.kafkaConsumer.commitSync();
    }

    private KafkaConsumer<String, String> initConsumer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        // 消息位移的提交策略, 默认自动提交
        // properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // 手动提交offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // 若设置自动提交, consumer内部有一个单独的线程定时进行位移, 该参数指定了这个时间间隔区间；
        // properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        return new KafkaConsumer<>(properties);
    }
}





