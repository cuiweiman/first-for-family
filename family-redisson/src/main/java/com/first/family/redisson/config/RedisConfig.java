package com.first.family.redisson.config;

import com.google.common.base.Preconditions;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

/**
 * @description: redis config
 * @author: cuiweiman
 * @date: 2023/6/5 14:28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "redisson")
public class RedisConfig {

    private static final String TYPE_SINGLE = "single";
    private static final String TYPE_CLUSTER = "cluster";

    /**
     * Common 参数
     */
    private String clusterType;
    private List<String> address;
    private String password;
    private Integer idleConnectionTimeout;
    private Integer connectTimeout;
    private Integer timeout;

    /**
     * Single 参数
     */
    private Integer database;
    private Integer connectionPoolSize;
    private Integer connectionMinimumIdleSize;

    /**
     * Cluster 参数
     */
    private Integer masterConnectionPoolSize;
    private Integer masterConnectionMinimumIdleSize;
    private Integer slaveConnectionPoolSize;
    private Integer slaveConnectionMinimumIdleSize;

    @Bean
    public RedissonClient redisson() throws InvalidPropertiesFormatException {
        checkCommon();
        Config config = new Config();
        if (TYPE_SINGLE.equalsIgnoreCase(clusterType)) {
            checkSingle();
            return this.singleServers(config);
        } else if (TYPE_CLUSTER.equalsIgnoreCase(clusterType)) {
            checkCluster();
            return this.clusterServers(config);
        }
        String errMsg = String.format("Redis 部署配置不支持, 只支持 single 单服务 和 cluster 集群. 当前配置: serverType = %s", clusterType);
        throw new RuntimeException(errMsg);
    }

    private RedissonClient singleServers(Config config) {
        SingleServerConfig singleServerConfig = config.useSingleServer().setAddress(address.get(0));
        singleServerConfig.setDatabase(database);
        if (StringUtils.hasText(password)) {
            singleServerConfig.setPassword(password);
        }
        // 连接池 最大容量
        singleServerConfig.setConnectionPoolSize(connectionPoolSize)
                // 最小连接数
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
                // 如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒
                .setIdleConnectionTimeout(idleConnectionTimeout)
                // 连接超时时间
                .setConnectTimeout(connectTimeout)
                // 服务器响应时间
                .setTimeout(timeout)
                .setDatabase(database);
        config.setCodec(new JsonJacksonCodec());
        return Redisson.create(config);
    }

    private RedissonClient clusterServers(Config config) {
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        clusterServersConfig.setNodeAddresses(address);
        if (StringUtils.hasText(password)) {
            clusterServersConfig.setPassword(password);
        }
        clusterServersConfig.setMasterConnectionPoolSize(masterConnectionPoolSize)
                .setMasterConnectionMinimumIdleSize(masterConnectionMinimumIdleSize)
                .setSlaveConnectionPoolSize(slaveConnectionPoolSize)
                .setSlaveConnectionMinimumIdleSize(slaveConnectionMinimumIdleSize)
                .setIdleConnectionTimeout(idleConnectionTimeout)
                .setConnectTimeout(connectTimeout)
                .setTimeout(timeout);
        config.setCodec(new JsonJacksonCodec());
        return Redisson.create(config);
    }

    private void checkCommon() throws InvalidPropertiesFormatException {
        Preconditions.checkNotNull(clusterType, "cluster-type must not be null");
        Preconditions.checkNotNull(idleConnectionTimeout, "idle-connection-timeout must not be null");
        Preconditions.checkNotNull(connectTimeout, "connection-timeout must not be null");
        Preconditions.checkNotNull(timeout, "timeout must not be null");
        checkAddress();
    }

    private void checkAddress() throws InvalidPropertiesFormatException {
        Preconditions.checkArgument(!ObjectUtils.isEmpty(address), "address must not be null");
        for (String add : address) {
            if (!add.startsWith("redis://")) {
                throw new InvalidPropertiesFormatException("address format error, should be redis://ip:port,redis://ip2:port2");
            }
        }
    }

    private void checkSingle() {
        Preconditions.checkNotNull(database, "database must not be null");
        Preconditions.checkNotNull(connectionMinimumIdleSize, "connection-minimum-idle-size must not be null");
        Preconditions.checkNotNull(connectionPoolSize, "connection-pool-size must not be null");
    }

    private void checkCluster() {
        Preconditions.checkNotNull(masterConnectionPoolSize, "master-connection-pool-size must not be null");
        Preconditions.checkNotNull(masterConnectionMinimumIdleSize, "master-connection-minimum-idle-size must not be null");
        Preconditions.checkNotNull(slaveConnectionPoolSize, "slave-connection-pool-size must not be null");
        Preconditions.checkNotNull(slaveConnectionMinimumIdleSize, "slave-connection-minimum-idle-size must not be null");
    }

}
