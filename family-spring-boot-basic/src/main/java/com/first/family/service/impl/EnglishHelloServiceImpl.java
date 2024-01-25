package com.first.family.service.impl;

import com.first.family.service.HelloService;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/12/20 18:49
 */
public class EnglishHelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("Hello, " + name);
        return "Hello, " + name;
    }
}
