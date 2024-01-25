package com.first.family.service;

import com.first.family.service.impl.EnglishHelloServiceImpl;
import com.first.family.service.impl.JapaneseHelloServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/12/20 19:05
 */
@Configuration
public class HelloServiceConfig {

    @Bean
    @ConditionalOnExpression("'${server.hello:English}'.equals('English')")
    public HelloService localCacheService() {
        return new EnglishHelloServiceImpl();
    }

    @Bean
    @ConditionalOnExpression("'${server.hello:English}'.equals('Japanese')")
    public HelloService redisCacheService() {
        return new JapaneseHelloServiceImpl();
    }

}
