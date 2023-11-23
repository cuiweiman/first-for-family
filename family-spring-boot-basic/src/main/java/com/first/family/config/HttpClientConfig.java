package com.first.family.config;

import jodd.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/11/8 14:13
 */
@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient httpClient() {
        int thread = Runtime.getRuntime().availableProcessors();
        ExecutorService httpExecutor = new ThreadPoolExecutor(thread, thread * 2,
                2, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024),
                new ThreadFactoryBuilder().setDaemon(false).setNameFormat("HttpClient-%s").get(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30L))
                .version(HttpClient.Version.HTTP_1_1)
                .executor(httpExecutor).build();
    }

}
