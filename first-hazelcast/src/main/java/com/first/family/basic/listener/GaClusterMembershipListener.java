package com.first.family.basic.listener;

import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;

/**
 * @description: 集群成员监听事件
 * @author: cuiweiman
 * @date: 2023/7/18 13:44
 */
public class GaClusterMembershipListener implements MembershipListener {

    @Override
    public void memberAdded(MembershipEvent membershipEvent) {
        String msg = String.format("member %s, type %s", membershipEvent.getMember(),
                membershipEvent.getEventType() == MembershipEvent.MEMBER_ADDED ? "added" : "removed");
        System.out.println(msg);
    }

    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {
        memberAdded(membershipEvent);
    }

}