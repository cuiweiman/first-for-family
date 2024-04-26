package concurrency.unsafes;

import concurrency.Singer;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/12/21 18:53
 */
public class UnsafeTest {
    public static void main(String[] args) {
        UnsafeTest demo = new UnsafeTest();
        demo.test();
    }

    public void test() {
        Unsafe unsafe = reflectGetUnsafe();
        try {
            // 通过反射创建一个实例
            Object singerInstance = unsafe.allocateInstance(Singer.class);
            System.out.println(singerInstance);
            // 获取对象的成员变量
            Field name = Singer.class.getDeclaredField("name");
            // 设置成员变量为可访问
            name.setAccessible(true);
            // 设置成员变量的值
            unsafe.putObject(singerInstance, unsafe.objectFieldOffset(name), "张三");
            // 获取成员变量的值
            String nameValue = (String) unsafe.getObject(singerInstance, unsafe.objectFieldOffset(name));
            System.out.println(singerInstance);
        } catch (InstantiationException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        // 分配 4个字节 的内存，并返回内存地址
        long allocateMemory = unsafe.allocateMemory(4);
        // 向该内存地址 写入 int 数据 10
        unsafe.putInt(allocateMemory, 10);
        System.out.println("内存地址:" + allocateMemory + ", 改地址的int值: " + unsafe.getInt(allocateMemory));
        // 释放内存
        unsafe.freeMemory(allocateMemory);
    }

    public static Unsafe reflectGetUnsafe() {
        try {
            Field unsafe = Unsafe.class.getDeclaredField("theUnsafe");
            unsafe.setAccessible(true);
            return (Unsafe) unsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
