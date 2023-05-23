package com.first.family.lambda;

import java.util.function.Function;

/**
 * @description: FunctionDemo
 * @author: cuiweiman
 * @date: 2023/2/15 16:48
 */
public class FunctionDemo {

    public static void main(String[] args) {
        Function<String, Integer> fun1 = String::length;
        Function<Integer, String> fun2 = (content) -> String.format("字符长度为: %d", content);
        String strLen = method(fun1, fun2, "HelloFunction");
        System.out.println(strLen);
    }

    /**
     * @param fun1    第一个处理函数，入参是 String，输出 Integer 类型
     * @param fun2    第二个处理函数，输入 Integer，输出 String 类型。
     * @param content 入参
     * @return func2 处理结果
     */
    private static String method(Function<String, Integer> fun1, Function<Integer, String> fun2, String content) {
        return fun1.andThen(fun2).apply(content);
    }

}
