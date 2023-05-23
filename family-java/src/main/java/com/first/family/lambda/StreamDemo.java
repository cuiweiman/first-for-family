package com.first.family.lambda;

import com.google.common.collect.Lists;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: Stream流式处理
 * @author: cuiweiman
 * @date: 2023/2/15 17:15
 */
public class StreamDemo {
    public static void main(String[] args) {
        filterList();
    }

    private static void filterList() {
        List<String> list = Lists.newArrayList("张三丰", "周芷若", "张无忌", "张王李赵", "黄药师", "张伟");
        List<String> nameList = list.stream().limit(2).filter((name) -> name.startsWith("张"))
                .filter((name) -> name.length() == 3).collect(Collectors.toList());
        PrintStream out = System.out;
        nameList.forEach(out::println);
    }

}
