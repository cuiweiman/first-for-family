package com.first.family.demo.apachecurator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

/**
 * 服务发现与注册的功能
 *
 * @description: curator-x-discovery: service discovery test
 * @author: cuiweiman
 * @date: 2023/3/1 16:03
 */
public class DiscoveryTest {

    @Test
    public void testBasicsServiceDiscovery() throws Exception {
        CuratorFramework client = null;
        ServiceDiscovery<String> discovery = null;
        ServiceProvider<String> provider = null;
        String serviceName = "test";
        String basePath = "/services";
        String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

        try {
            client = CuratorFrameworkFactory.newClient(connectString, new RetryOneTime(1));
            client.start();

            ServiceInstance<String> instance1 = ServiceInstance.<String>builder()
                    .payload("plant").name(serviceName).port(10064).build();
            ServiceInstance<String> instance2 = ServiceInstance.<String>builder()
                    .payload("animal").name(serviceName).port(10065).build();
            System.out.printf("instance1 id : %s\n", instance1.getId());
            System.out.printf("instance2 id : %s\n", instance2.getId());

            discovery = ServiceDiscoveryBuilder.builder(String.class)
                    .basePath(basePath).client(client).thisInstance(instance1).build();
            discovery.start();
            discovery.registerService(instance2);

            provider = discovery.serviceProviderBuilder().serviceName(serviceName).build();
            provider.start();
            Collection<ServiceInstance<String>> allInstances = provider.getAllInstances();
            for (ServiceInstance<String> allInstance : allInstances) {
                System.out.println(allInstance);
            }
            Assertions.assertNotNull(provider.getInstance().getId());
            Assertions.assertTrue(allInstances.contains(instance1)
                    && allInstances.contains(instance2));

            client.delete().deletingChildrenIfNeeded().forPath(basePath);
        } finally {
            CloseableUtils.closeQuietly(provider);
            CloseableUtils.closeQuietly(discovery);
            CloseableUtils.closeQuietly(client);
        }


    }

}
