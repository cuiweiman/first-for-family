package com.first.family;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

/**
 * SpringBootApplication 等价于 SpringBootConfiguration + EnableAutoConfiguration + ComponentScan
 *
 * @description: springboot basic
 * @author: cuiweiman
 * @date: 2023/4/5 17:23
 */
@SpringBootApplication
public class SpringBootBasic {
    public static void main(String[] args) {
        // ioc 容器上下文
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootBasic.class, args);
        // 查看容器中所有的Bean组件
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.asList(beanDefinitionNames).forEach(System.out::println);
    }
}