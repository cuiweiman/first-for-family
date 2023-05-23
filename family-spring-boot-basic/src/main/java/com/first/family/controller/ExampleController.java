package com.first.family.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: as an example
 * @author: cuiweiman
 * @date: 2023/4/5 17:35
 */
@RestController
public class ExampleController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello01() {
        return "Hello Spring Boot 2.0x 中文编码 ";
    }


}
