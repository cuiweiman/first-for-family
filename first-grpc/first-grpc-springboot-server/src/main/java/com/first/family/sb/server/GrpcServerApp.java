package com.first.family.sb.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/7/2 13:24
 */
@Slf4j
@SpringBootApplication
public class GrpcServerApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(GrpcServerApp.class, args);
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
                    "\tGrpcServerApp is running! (^_^) PID: " + pid + "\n" +
                    "\tLocal: \t\thttp://localhost:" + port + path + "/\n" +
                    "\tExternal: \thttp://" + ip + ":" + port + path + "/\n" +
                    // "Swagger: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                    "-------------------------------------------------------------");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
