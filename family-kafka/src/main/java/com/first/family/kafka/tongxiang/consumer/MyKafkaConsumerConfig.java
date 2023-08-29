package com.first.family.kafka.tongxiang.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
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
public class MyKafkaConsumerConfig {

    @Value("${kafka.bootstrap-servers:}")
    private String bootstrapServers;

    @Value("${kafka.group-id:}")
    private String groupId;

    @Value("${kafka.password:}")
    private String password;


    @Bean(name = "kafkaFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, byte[]>> kafkaFactory() throws FileNotFoundException {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        // 创建消费的kafka工厂对象
        ConsumerFactory<String, byte[]> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProperties());
        factory.setConsumerFactory(consumerFactory);
        // 手动提交 - 关闭
        // factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }


    private Map<String, Object> consumerProperties() throws FileNotFoundException {
        // kafka的相关参数 比如ip地址和分组这些参数
        Map<String, Object> properties = new HashMap<>();
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
                // "/Users/cuiweiman/Downloads/kafka-key/server.keystore.jks");
        ResourceUtils.getFile("classpath:server.keystore.jks").getPath());
        properties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, password);
        //设置为空字符串来禁用服务器主机名验证
        properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        properties.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, password);
        //获取Resources配置文件中client.truststore.jks
        properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,
                // "/Users/cuiweiman/Downloads/kafka-key/server.truststore.jks");
        ResourceUtils.getFile("classpath:server.truststore.jks").getPath());
        properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, password);

        return properties;
    }


}
