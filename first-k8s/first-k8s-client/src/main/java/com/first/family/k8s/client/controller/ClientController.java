package com.first.family.k8s.client.controller;

import com.first.family.k8s.client.feign.ServerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 客户端测试
 * @author: cuiweiman
 * @date: 2023/7/5 19:56
 */
@Slf4j
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ClientController {

    private final ServerClient serverClient;

    @GetMapping("/health")
    public String health() {
        return "success";
    }

    @GetMapping("/callServer")
    public String callServer() {
        return serverClient.ping();
    }

}
