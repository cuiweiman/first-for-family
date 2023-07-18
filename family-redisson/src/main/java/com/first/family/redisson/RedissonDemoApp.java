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
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RedissonDemoApp.class, args);
        applicationContext.registerShutdownHook();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = environment.getProperty("server.port");
            String path = Optional
                    .ofNullable(environment.getProperty("server.servlet.context-path")).orElse("");
            String jdkVersion = (String) environment.getSystemProperties().get("java.version");
            String jdkVm = (String) environment.getSystemProperties().get("java.vm.name");
            String pid = (String) environment.getSystemProperties().get("PID");
            log.info("\n-------------------------------------------------------------\n" +
                    "\tJDK version: " + jdkVm + " " + jdkVersion + "\n" +
                    "\tRedissonDemoApp is running! (^_^) PID: " + pid + "\n" +
                    "\tLocal: \t\thttp://localhost:" + port + path + "/\n" +
                    "\tExternal: \thttp://" + ip + ":" + port + path + "/\n" +
                    // "Swagger: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                    "-------------------------------------------------------------");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
