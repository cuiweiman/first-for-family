package com.first.family.concurrency.varhandles;

import com.first.family.concurrency.Singer;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * @description: VarHandle API 使用学习
 * @author: cuiweiman
 * @date: 2023/12/21 18:00
 */
public class VarHandleTest {

    public static void main(String[] args) {
        VarHandleTest demo = new VarHandleTest();
        demo.privateProtectedPublic();
    }

    /**
     * 类似 反射
     * 获取私有属性
     * 获取受保护属性
     * 获取公有属性
     * CAS 原子操作
     *
     * @see VarHandle 不能实例化对象，只能操作对象属性
     */
    public void privateProtectedPublic() {
        Singer singer = new Singer();
        System.out.println(singer);
        try {
            // 获取 private 私有属性
            VarHandle privateAge = MethodHandles.privateLookupIn(Singer.class, MethodHandles.lookup())
                    .findVarHandle(Singer.class, "age", Integer.class);
            privateAge.set(singer, 10);
            // CAS 原子操作 更新
            System.out.println(privateAge.compareAndSet(singer, 10, 28));

            // 获取 protected 受保护 属性
            VarHandle protectedSex = MethodHandles.privateLookupIn(Singer.class, MethodHandles.lookup())
                    .findVarHandle(Singer.class, "sex", String.class);
            protectedSex.set(singer, "男");

            // 获取 public 公有属性 数组
            VarHandle publicYears = MethodHandles.arrayElementVarHandle(Integer[].class);
            publicYears.set(singer.years, 1, 1990);
            System.out.println(singer);

            // 构造新的 Singer 对象


        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
