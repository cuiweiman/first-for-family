package com.first.family.k8s.client.feign.impl;

import com.first.family.k8s.client.feign.ServerClient;
import org.springframework.stereotype.Component;

/**
 * @description: 服务熔断
 * @author: cuiweiman
 * @date: 2023/7/5 20:01
 */
@Component
public class ServerClientFallback implements ServerClient {

    @Override
    public String ping() {
        return "k8s-server error";
    }
}
