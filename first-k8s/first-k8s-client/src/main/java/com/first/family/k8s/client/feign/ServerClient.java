package com.first.family.k8s.client.feign;

import com.first.family.k8s.client.feign.impl.ServerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description: 服务端Feign接口
 * @author: cuiweiman
 * @date: 2023/7/5 20:00
 * @see FeignClient 远程调用客户端
 * @see FeignClient#name() 远程服务名称+远程服务path, k8s 中需要是 service name。
 */
@FeignClient(name = "server-service", fallback = ServerClientFallback.class)
public interface ServerClient {

    @GetMapping("/server/ping")
    String ping();

}
