package com.first.family.kafka.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Objects;
import java.util.Properties;

/**
 * @description: producer demo
 * @author: cuiweiman
 * @date: 2023/5/17 15:18
 */
public class ProducerDemo {

    private final KafkaProducer<String, String> kafkaProducer;

    public ProducerDemo() {
        // 创建KafkaProducer对象，该对象是用来进行消息发送
        this.kafkaProducer = initProducer();
    }

    public void msgSend(String topic, String key, String value) {
        // 创建 ProducerRecord封装消息, 再通过 producer 进行 异步回调方式的 发送
        ProducerRecord<String, String> orderInfo = new ProducerRecord<>(topic, key, value);
        kafkaProducer.send(orderInfo, (metadata, exception) -> {
            if (Objects.nonNull(exception)) {
                System.out.println(Thread.currentThread().getId()
                        + " 消息发送异常, KafkaProducer关闭 " + exception.getMessage());
                kafkaProducer.close();
                throw new RuntimeException(exception);
            } else {
                String format = String.format("topic: %s, partition: %d, offset: %d, ",
                        topic, metadata.partition(), metadata.offset());
                System.out.println(format);
            }
        });
    }

    public KafkaProducer<String, String> initProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        /*
        可选的值有四个 0、1、-1或all:
            0:不管目标broker是否返回响应或者是否发生错误,直接执行后续的逻辑;(吞吐量)
            1:producer发送消息之后,接收消息的broker写入完成后就直接返回响应;
            -1和all:消息发送到broker之后, 需要其所在分区的所有副本都必须写入成功之后才给producer发送响应;(持久性要求比较高)
         */
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        // 消息发送失败的重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        // producer发送消息时,先存储到本地缓存队列中,数据大小达到指定数据时,批量的发送消息.该参数默认16KB,设置为32KB;
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 323840);
        // 批量数据达到,或者等待时间达到,均可触发消息批量发送
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        // 每个消息的每个分区都有一个缓冲区, 当消息过多时, producer的缓冲区就会比较大, 这个参数主要限定总缓冲区的大小；
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        // 指定 producer 端最多阻塞的时长
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 3000);
        // 自定义分区发送
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, PartitionDemo.class.getName());
        // lz4 或 zstd, kafka适配最好的两种压缩算法
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"lz4");
        return new KafkaProducer<>(properties);
    }
}