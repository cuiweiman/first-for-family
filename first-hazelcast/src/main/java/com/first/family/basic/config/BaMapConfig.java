package com.first.family.basic.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/7/17 15:58
 */
public class BaMapConfig {

    private BaMapConfig() {
    }

    public static Config mapConfig() {
        // 同步备份 配置, backup-count 可设置的最大值为6。
        MapConfig mapSyncConfig = new MapConfig();
        mapSyncConfig.setBackupCount(2);
        // 备份数据可读, 会影响数据一致性, 因此默认是false。
        // 开启后可以提高 读性能, 但是可能出现脏读。
        mapSyncConfig.setReadBackupData(true);
        // Map 驱逐策略配置::每条数据在map中的保存时间,如果大于0,数据的存活时间超过参数值时会被自动驱逐
        mapSyncConfig.setTimeToLiveSeconds(0);
        // Map 驱逐策略配置::数据在map中的最大空闲时间(自最后一次操作以来的时间), 0表示无穷大
        mapSyncConfig.setMaxIdleSeconds(0);

        // Map 驱逐策略配置::数据量超过设置的最大值时采用的驱逐策略:LRU 最近最少使用策略
        EvictionConfig evictionConfig = new EvictionConfig();
        evictionConfig.setEvictionPolicy(EvictionPolicy.LRU);
        // 集群节点存储的最大数据量,默认 PER_NODE, 每个节点存储的最大数量
        evictionConfig.setMaxSizePolicy(MaxSizePolicy.PER_NODE);
        // 数据量超过该参数的值时, map会基于设置的驱逐策略对数据驱逐
        evictionConfig.setSize(5000);
        mapSyncConfig.setEvictionConfig(evictionConfig);


        // 异步备份 配置, map可以同时进行 同步备份 和 异步备份。
        MapConfig mapAsyncConfig = new MapConfig();
        mapSyncConfig.setBackupCount(0);
        mapAsyncConfig.setAsyncBackupCount(2);
        mapAsyncConfig.setReadBackupData(false);

        Config config = CommonConfig.baseConfig();
        Map<String, MapConfig> mapConfigs = new HashMap<>(4);
        mapConfigs.put("map-sync", mapSyncConfig);
        mapConfigs.put("map-async", mapAsyncConfig);

        config.setMapConfigs(mapConfigs);
        return config;
    }

}
