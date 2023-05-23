package com.first.family;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @description: Zookeeper 应用
 * @author: cuiweiman
 * @date: 2022/11/10 10:57
 */
@SpringBootApplication
public class ZkApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZkApplication.class)
                .web(WebApplicationType.SERVLET)
                .registerShutdownHook(true)
                .run(args);
    }

}
