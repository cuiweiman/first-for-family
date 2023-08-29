package com.first.shardingsphere;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * @description: 启动类
 * @author: cuiweiman
 * @date: 2023/7/27 16:13
 */
@Slf4j
@SpringBootApplication
public class ShardingSphereApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ShardingSphereApp.class, args);
        applicationContext.registerShutdownHook();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = Optional.ofNullable(environment.getProperty("server.port")).orElse("");
            String path = Optional
                    .ofNullable(environment.getProperty("server.servlet.context-path")).orElse("");
            String jdkVersion = (String) environment.getSystemProperties().get("java.version");
            String jdkVm = (String) environment.getSystemProperties().get("java.vm.name");
            String pid = (String) environment.getSystemProperties().get("PID");
            log.info("\n-------------------------------------------------------------\n" +
                    "\tJDK version: " + jdkVm + " " + jdkVersion + "\n" +
                    "\tShardingSphereApp is running! (^_^) PID: " + pid + "\n" +
                    "\tLocal: \t\thttp://localhost:" + port + path + "/\n" +
                    "\tExternal: \thttp://" + ip + ":" + port + path + "/\n" +
                    // "Swagger: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                    "-------------------------------------------------------------");
        } catch (UnknownHostException e) {
            log.error("application run error.", e);
        }
    }
}
