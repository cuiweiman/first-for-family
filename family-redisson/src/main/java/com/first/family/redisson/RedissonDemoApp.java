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
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RedissonDemoApp.class, args);
        applicationContext.registerShutdownHook();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        String jdkVm = (String) environment.getSystemProperties().get("java.vm.name");
        String jdkVersion = (String) environment.getSystemProperties().get("java.version");
        String appName = environment.getProperty("spring.application.name");
        String pid = (String) environment.getSystemProperties().get("PID");
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port", "8080");
        String path = Optional.ofNullable(environment.getProperty("server.servlet.context-path")).orElse("");
        log.info("""
                                
                -------------------------------------------------------------
                    JDK version: %s %s
                    应用 %s 启动成功!  (^_^)  PID: %s
                    访问链接:
                        Knife4j 接口文档:   http://%s:%s%s/doc.html
                        Druid 数据库监控:    http://%s:%s%s/druid
                -------------------------------------------------------------
                """.formatted(jdkVm, jdkVersion, appName, pid, ip, port, path, "127.0.0.1", port, path)
        );

    }
}
