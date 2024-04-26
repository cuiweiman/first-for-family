package com.first.family.rmq.config;

import com.first.family.rmq.consumer.RabbitConsumer;
import com.rabbitmq.client.BlockedListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2024/4/26 09:43
 */
@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RabbitConfig {

    @Value("${rabbitmq.host}")
    private String rmqHost;

    @Value("${rabbitmq.port}")
    private Integer rmqPort;

    @Value("${rabbitmq.user}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.vHost}")
    private String vHost;

    @Value("${rabbitmq.queue}")
    private String queue;

    @Value("${rabbitmq.consumer:15}")
    private Integer consumer;

    private final RabbitConsumer rabbitConsumer;


    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(new Queue(queue));
        container.setMaxConcurrentConsumers(consumer);
        container.setConcurrentConsumers(consumer);
        // 手动确认
        container.setAcknowledgeMode(AcknowledgeMode.NONE);
        container.setMessageListener(rabbitConsumer);
        return container;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(rmqHost, rmqPort);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vHost);
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        int threadNum = Runtime.getRuntime().availableProcessors();
        ExecutorService amqpConnection = new ThreadPoolExecutor(threadNum, 2 * threadNum, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(128), r -> {
            Thread thread = new Thread(r);
            thread.setName("amqp-connection");
            return thread;
        });
        connectionFactory.setExecutor(amqpConnection);
        connectionFactory.addConnectionListener(connection -> connection.addBlockedListener(new BlockedListener() {
            @Override
            public void handleBlocked(String reason) {
                log.error("rabbitmq connection blocked, reason {}", reason);
            }

            @Override
            public void handleUnblocked() throws IOException {
                log.info("rabbitmq connection unblocked");
            }
        }));
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        /*
         * 消息达到队列与否 都会触发
         */
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * @param correlationData correlation data for the callback.回调的相关数据
             * @param ack true for ack, false for nack. true表示成功回调,false 表示失败回调
             * @param cause 可能的原因，针对 nack 响应（如果可用），否则为 null。
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.printf("消息发送是否成功 %s, cause %s, CorrelationData %s %n", ack, cause, correlationData);
            }
        });

        /*
         * 交换机的 消息 没有 到达队列 时 触发
         */
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                System.out.printf("消息发送失败 ReturnedMessage %s %n", returned);
            }
        });

        return rabbitTemplate;
    }

}
