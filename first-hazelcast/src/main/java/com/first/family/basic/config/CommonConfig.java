package com.first.family.basic.config;

import com.hazelcast.config.AutoDetectionConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.jet.config.JetConfig;

import java.util.List;

/**
 * @description: A_CreateOrDestroy
 * @author: cuiweiman
 * @date: 2023/7/17 15:07
 */
public class CommonConfig {

    private CommonConfig() {
    }

    public static Config baseConfig() {
        Config config = new Config();
        config.setNetworkConfig(networkConfig());

        JetConfig jetConfig = new JetConfig();
        jetConfig.setEnabled(true);
        config.setJetConfig(jetConfig);

        return config;
    }

    public static Config baseConfig(String clusterName) {
        Config config = new Config();
        config.setClusterName(clusterName);
        config.setNetworkConfig(networkConfig());

        JetConfig jetConfig = new JetConfig();
        jetConfig.setEnabled(true);
        config.setJetConfig(jetConfig);

        return config;
    }

    public static NetworkConfig networkConfig() {
        // 集群的节点发现机制::单播
        TcpIpConfig tcpIpConfig = new TcpIpConfig();
        tcpIpConfig.setEnabled(true);
        tcpIpConfig.setConnectionTimeoutSeconds(180);
        tcpIpConfig.setMembers(List.of("localhost"));

        AutoDetectionConfig autoDetectionConfig = new AutoDetectionConfig();
        autoDetectionConfig.setEnabled(true);

        JoinConfig joinConfig = new JoinConfig();
        joinConfig.setAutoDetectionConfig(autoDetectionConfig);
        joinConfig.setTcpIpConfig(tcpIpConfig);

        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setJoin(joinConfig);
        networkConfig.setPort(5701);
        networkConfig.setPortAutoIncrement(true);
        networkConfig.setPortCount(100);
        networkConfig.setOutboundPorts(List.of(0));
        return networkConfig;
    }

}
