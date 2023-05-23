package com.first.family.lambda;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @description: ConsumerDemo
 * @author: cuiweiman
 * @date: 2023/2/15 14:25
 */
public class ConsumerDemo {

    public static void main(String[] args) {
        int[] arr = {3, 6, 1, 0, 9, 7, 5};
        consumerDemo((array) -> {
            int max = Arrays.stream(array).max().getAsInt();
            System.out.println(max);
        }, arr);

        consumerDemo((array) -> {
            int max = Arrays.stream(array).min().getAsInt();
            System.out.println(max);
        }, arr);

        Consumer<String> first = (name) -> System.out.println(name.concat(" 杰森"));
        Consumer<String> second = (name) -> System.out.println(name.concat(" 郭达"));
        consumerThen(first, second, "斯坦森");
    }

    private static <T> void consumerDemo(Consumer<T> consumer, T t) {
        if (Objects.nonNull(consumer) && Objects.nonNull(t)) {
            consumer.accept(t);
        }
    }

    private static <T> void consumerThen(Consumer<T> first, Consumer<T> second, T t) {
        // first先消费t; second 再消费。两者逻辑上分别处理入参内容 t，不互相依赖
        first.andThen(second).accept(t);
    }

}
