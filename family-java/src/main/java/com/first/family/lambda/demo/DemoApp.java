package com.first.family.lambda.demo;

/**
 * @description: demo
 * @author: cuiweiman
 * @date: 2023/2/15 13:16
 */
public class DemoApp {
    public static void main(String[] args) {
        // Lambda表达式 实现 接口匿名类
        Factory userFactory = () -> new UserInfo("Saber", 19);
        UserInfo userInfo = (UserInfo) userFactory.getObject();
        System.out.println(userInfo);

        // Lambda表达式作为入参
        UserInfo userInfo2 = getObjectFromFactory(() -> new UserInfo("Archer", 28), UserInfo.class);
        System.out.println(userInfo2);
    }

    public static <T> T getObjectFromFactory(Factory factory, Class<T> clazz) {
        return clazz.cast(factory.getObject());
    }

}
