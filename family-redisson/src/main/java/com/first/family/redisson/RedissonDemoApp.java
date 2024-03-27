package com.first.family.redisson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * @description: main
 * @author: cuiweiman
 * @date: 2023/6/5 13:48
 */
@Slf4j
@SpringBootApplication
public class RedissonDemoApp {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringApplication.class, args);
        applicationContext.registerShutdownHook();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        String jdkVm = (String) environment.getSystemProperties().get("java.vm.name");
        String jdkVersion = (String) environment.getSystemProperties().get("java.version");
        String appName = environment.getProperty("spring.application.name");
        String pid = (String) environment.getSystemProperties().get("PID");
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port", "8080");
        String path = Optional.ofNullable(environment.getProperty("server.servlet.context-path")).orElse("");

        log.info("%n-------------------------------------------------------------%n"
                        + "%t JDK version: {} {} %n" + "%t 应用 '{}' 启动成功!  (^_^)  PID: {} %n"
                        + "%t 访问连接:%n"
                        + "%t%t Knife4j 接口文档: %t%t http://{}:{}{}/doc.html%n"
                        + "%t%t Druid 数据库监控: %t%t http://{}:{}{}/druid%n",
                jdkVm, jdkVersion, appName, pid, ip, port, path, "127.0.0.1", port, path);

    }
}
