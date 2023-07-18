package com.first.family.basic;

import com.first.family.basic.config.CommonConfig;
import com.first.family.basic.listener.GaClusterMembershipListener;
import com.first.family.basic.utils.SleepUtil;
import com.hazelcast.config.Config;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * @description: 监听成员事件 https://www.jianshu.com/p/6fba643313b5
 * @author: cuiweiman
 * @date: 2023/7/18 11:30
 */
public class GaClusterMemberListener {

    public static void main(String[] args) {
        ListenerConfig listenerConfig = new ListenerConfig(new GaClusterMembershipListener());

        Config config = CommonConfig.baseConfig("MemberListener");
        config.addListenerConfig(listenerConfig);

        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        SleepUtil.sleep(2);
        HazelcastInstance instance2 = Hazelcast.newHazelcastInstance(config);
        SleepUtil.sleep(2);
        instance.shutdown();
        SleepUtil.sleep(2);
        HazelcastInstance instance3 = Hazelcast.newHazelcastInstance(config);
        SleepUtil.sleep(2);
        instance2.shutdown();
        instance3.shutdown();
    }

}
