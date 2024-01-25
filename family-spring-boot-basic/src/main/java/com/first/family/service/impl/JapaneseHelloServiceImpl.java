package com.first.family.service.impl;

import com.first.family.service.HelloService;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/12/20 18:49
 */
public class JapaneseHelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        System.out.println("こんにちは " + name);
        return "こんにちは " + name;
    }
}
