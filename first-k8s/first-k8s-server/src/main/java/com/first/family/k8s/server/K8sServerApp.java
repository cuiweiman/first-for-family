package com.first.family.k8s.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * 启用服务发现 {@link EnableDiscoveryClient}
 *
 * @description: k8s服务发现 服务端模拟
 * @author: cuiweiman
 * @date: 2023/7/5 19:35
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class K8sServerApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(K8sServerApp.class, args);
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
                    "\tK8sServerApp is running! (^_^) PID: " + pid + "\n" +
                    "\tLocal: \t\thttp://localhost:" + port + path + "/\n" +
                    "\tExternal: \thttp://" + ip + ":" + port + path + "/\n" +
                    // "Swagger: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                    "-------------------------------------------------------------");
        } catch (UnknownHostException e) {
            log.error("application run error.", e);
        }
    }
}
