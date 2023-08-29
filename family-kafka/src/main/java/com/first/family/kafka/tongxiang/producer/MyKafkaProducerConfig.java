package com.first.family.kafka.tongxiang.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/8/17 14:33
 */
@Slf4j
@Configuration
public class MyKafkaProducerConfig {

    @Value("${kafka.bootstrap-servers:}")
    private String bootstrapServers;

    @Value("${kafka.password:}")
    private String password;

    /**
     * 注入 kafkaTemplate
     */
    @Bean
    public KafkaTemplate<String, String> myKafkaTemplate() throws FileNotFoundException {
        Map<String, Object> map = producerProperties();
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(map);
        return new KafkaTemplate<>(producerFactory);
    }


    /**
     * kafka的配置
     */
    private Map<String, Object> producerProperties() throws FileNotFoundException {
        // kafka的相关参数 比如ip地址和分组这些参数
        Map<String, Object> properties = new HashMap<>(32);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // timeout 时长
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 20000);

        /*
        消息发送失败的重试次数
        可选的值有四个 0、1、-1或all:
            0:不管目标broker是否返回响应或者是否发生错误,直接执行后续的逻辑;(吞吐量)
            1:producer发送消息之后,接收消息的broker写入完成后就直接返回响应;
            -1和all:消息发送到broker之后, 需要其所在分区的所有副本都必须写入成功之后才给producer发送响应;(持久性要求比较高)
         */
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        properties.put(ProducerConfig.RETRIES_CONFIG, 1);
        // producer 发送消息时,先存储到本地缓存队列中,数据大小达到指定数据时,批量的发送消息.该参数默认16KB,设置为32KB;
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 323840);
        // 批量数据达到,或者等待时间达到,均可触发消息批量发送
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        // 每个消息的每个分区都有一个缓冲区, 当消息过多时, producer的缓冲区就会比较大, 这个参数主要限定总缓冲区的大小；
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        // 指定 producer 端最多阻塞的时长
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 3000);
        // lz4 或 zstd, kafka适配最好的两种压缩算法
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");

        //ssl加密和认证配置
        properties.put(SslConfigs.SSL_PROTOCOL_CONFIG, "SSL");
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        properties.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, "JKS");
        properties.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, "JKS");

        //获取 resources 配置文件中 server.keystore.jks
        properties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG,
                // "/Users/cuiweiman/Downloads/server.keystore.jks");
        ResourceUtils.getFile("classpath:server.keystore.jks").getPath());
        properties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, password);
        //获取 resources 配置文件中 server.truststore.jks
        properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,
                // "/Users/cuiweiman/Downloads/server.truststore.jks");
        ResourceUtils.getFile("classpath:server.keystore.jks").getPath());
        properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, password);
        //设置为空字符串来禁用服务器主机名验证
        properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        properties.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, password);

        return properties;
    }

}
