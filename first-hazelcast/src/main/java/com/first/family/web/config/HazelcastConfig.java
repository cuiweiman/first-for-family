package com.first.family.web.config;

import com.hazelcast.config.AutoDetectionConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.impl.HazelcastInstanceFactory;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码配置太繁琐了，不如 yaml 或者 xml 配置方式
 *
 * @description: 代码配置示例, hazelcast.yml 也可以实现配置
 * @author: cuiweiman
 * @date: 2023/7/12 09:57
 */
@Configuration
public class HazelcastConfig {

    private static final String CACHE_NAME = "my_cache";
    private static final String DISTRIBUTED_MAP = "my-distributed-map";

    @Bean
    public IMap<String, String> iMap(@Qualifier("myHazelcastInstance") HazelcastInstance myHazelcastInstance) {
        return myHazelcastInstance.getMap(DISTRIBUTED_MAP);
    }

    @Bean
    public <K, V> Cache<K, V> jCache() {
        // Run as a Hazelcast Client
        System.setProperty("hazelcast.jcache.provider.type", "client");
        MutableConfiguration<K, V> configuration = new MutableConfiguration<>();
        configuration.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
        return cacheManager.createCache(CACHE_NAME, configuration);
    }

    @Bean
    public HazelcastInstance myHazelcastInstance(NetworkConfig networkConfig,
                                                 Map<String, MapConfig> mapConfigs) {
        Config config = new Config();
        // 集群名称, 使用同一个集群名称的节点才能组合成一个集群
        config.setClusterName("dev");
        config.setNetworkConfig(networkConfig);
        config.setMapConfigs(mapConfigs);

        return HazelcastInstanceFactory.newHazelcastInstance(config);
    }

    /**
     * 网络配置——节点发现机制配置：
     * TCP是单播, 面向连接的可靠传输协议, 需要ack确认报文。
     * Multicast多播基于UDP, 可以实现一对多。
     *
     * @return 节点发现机制配置信息
     */
    @Bean
    public NetworkConfig networkConfig() {
        // 集群的节点发现机制::多播
        MulticastConfig multicastConfig = new MulticastConfig();
        multicastConfig.setEnabled(false);
        multicastConfig.setMulticastGroup("224.2.2.3");
        multicastConfig.setMulticastPort(54327);
        // 集群的节点发现机制::单播
        TcpIpConfig tcpIpConfig = new TcpIpConfig();
        tcpIpConfig.setEnabled(true);
        tcpIpConfig.setConnectionTimeoutSeconds(180);
        tcpIpConfig.setMembers(List.of("localhost"));

        AutoDetectionConfig autoDetectionConfig = new AutoDetectionConfig();
        autoDetectionConfig.setEnabled(true);

        JoinConfig joinConfig = new JoinConfig();
        joinConfig.setAutoDetectionConfig(autoDetectionConfig);
        joinConfig.setMulticastConfig(multicastConfig);
        joinConfig.setTcpIpConfig(tcpIpConfig);

        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setJoin(joinConfig);
        networkConfig.setPort(5701);
        networkConfig.setPortAutoIncrement(true);
        networkConfig.setPortCount(100);
        networkConfig.setOutboundPorts(List.of(0));
        return networkConfig;
    }

    @Bean
    public Map<String, MapConfig> mapConfigs() {
        Map<String, MapConfig> mapConfigs = new HashMap<>(4);
        MapConfig mapConfig = new MapConfig();
        // 可以同时开启 同步备份和异步备份
        mapConfig.setBackupCount(1);
        mapConfig.setAsyncBackupCount(1);
        mapConfigs.put(DISTRIBUTED_MAP, mapConfig);
        return mapConfigs;
    }

}
