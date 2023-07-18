package com.first.family.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * Java客户端
 * <a url="https://hazelcast.com/clients/java/">Hazelcast Java Client</a>
 * 参考大神up主
 * <a url="https://www.jianshu.com/p/a015ffb2dd8f">分布式map</a>
 *
 * @description: hazelcast 集成
 * @author: cuiweiman
 * @date: 2023/7/10 17:54
 */
@Slf4j
@SpringBootApplication
public class HazelcastApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(HazelcastApp.class, args);
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
                    "\tHazelcastApp is running! (^_^) PID: " + pid + "\n" +
                    "\tLocal: \t\thttp://localhost:" + port + path + "/\n" +
                    "\tExternal: \thttp://" + ip + ":" + port + path + "/\n" +
                    // "Swagger: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                    "-------------------------------------------------------------");
        } catch (UnknownHostException e) {
            log.error("主程序启动失败", e);
        }
    }
}
