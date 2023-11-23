package com.first.family.config;

import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/11/6 13:52
 */
@Configuration
public class RedissonConfig {

    public static final String SSE_COMPLETE_QUEUE = "search-server:sse-emitter:complete";
    public static final String SSE_CHAT_QUEUE = "search-server:sse-emitter:chat";

    @Bean
    public RTopic remoteCompleteQueue(RedissonClient redissonClient) {
        return redissonClient.getTopic(SSE_COMPLETE_QUEUE);
    }

    @Bean
    public RTopic remoteSendQueue(RedissonClient redissonClient) {
        return redissonClient.getTopic(SSE_CHAT_QUEUE);
    }

    @Bean
    public RedissonClient redissonClient() throws IOException {
        Config config = Config.fromYAML(
                RedissonConfig.class.getClassLoader().getResource("redisson.yml"));
        return Redisson.create(config);
    }
}
