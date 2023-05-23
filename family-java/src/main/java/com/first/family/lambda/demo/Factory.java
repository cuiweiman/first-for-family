package com.first.family.lambda.demo;

/**
 * @description: factory
 * @author: cuiweiman
 * @date: 2023/2/15 13:15
 */
@FunctionalInterface
public interface Factory {

    /**
     * 获取对象
     *
     * @return object
     */
    Object getObject();

}
