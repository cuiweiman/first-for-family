package com.first.family.lambda.eventbus;

import com.google.common.eventbus.EventBus;

/**
 * @description: eventbus
 * @author: cuiweiman
 * @date: 2023/5/26 14:01
 */
public class MultipleEventBus {
    public static void main(String[] args) {
        final EventBus bus = new EventBus();
        bus.register(new MultipleEventListener());
        bus.post("STRING EVENT A");
        bus.post("STRING EVENT B");
        bus.post("STRING EVENT C");
        bus.post(123456);
    }
}
