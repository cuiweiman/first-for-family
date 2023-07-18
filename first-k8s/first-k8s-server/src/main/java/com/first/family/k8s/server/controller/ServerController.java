package com.first.family.k8s.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.kubernetes.commons.KubernetesClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 获取k8s相关服务信息，以及 "ping" 远程调用测试接口
 * @author: cuiweiman
 * @date: 2023/7/5 19:38
 */
@RestController
@RequestMapping("/server")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ServerController {

    private final DiscoveryClient discoveryClient;
    private final KubernetesClientProperties k8sProperties;

    @GetMapping("/health")
    public String health() {
        return "success";
    }

    @GetMapping("/ping")
    public String ping() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "pone";
        }
    }

    @GetMapping("/discovery")
    public Map<String, Object> discoveryServiceList() {
        List<String> services = discoveryClient.getServices();
        int order = discoveryClient.getOrder();
        String description = discoveryClient.description();

        HashMap<String, Object> result = new HashMap<>(4);
        result.put("order", order);
        result.put("description", description);
        result.put("services", services);
        return result;
    }

    @GetMapping("/service/{serviceId}")
    public List<ServiceInstance> getByServiceId(@PathVariable String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }

    @GetMapping("/k8sProperties")
    public KubernetesClientProperties k8sProperties() {
        return k8sProperties;
    }

}
