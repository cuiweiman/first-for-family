package com.first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: Mybatis + Seata
 * @author: cuiweiman
 * @date: 2023/8/16 15:27
 */
@SpringBootApplication
public class MySeataApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySeataApplication.class, args);
        System.out.println("\t http://127.0.0.1:8890/test");
    }

}
