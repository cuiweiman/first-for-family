package com.first.family.controller;

import com.first.family.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: as an example
 * @author: cuiweiman
 * @date: 2023/4/5 17:35
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ExampleController {

    private final HelloService helloService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String hello01() {
        return "Hello Spring Boot 2.0x 中文编码 ";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello02() {
        return helloService.sayHello("Zachary");
    }

}
