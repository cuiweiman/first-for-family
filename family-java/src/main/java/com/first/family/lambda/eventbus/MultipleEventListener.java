package com.first.family.lambda.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * @description: eventbus listener
 * @author: cuiweiman
 * @date: 2023/5/26 14:01
 */
public class MultipleEventListener {

    @Subscribe
    private void task1(String event) {
        System.out.println("【task1】Received event [{}] and will take a action." + event);
    }

    @Subscribe
    private void task2(String event) {
        System.out.println("【task2】Received event [{}] and will take a action." + event);
    }

    @Subscribe
    private void integerTask(Integer event) {
        System.out.println("【intTask】Received event [{}] and will take a action." + event);
    }
}
