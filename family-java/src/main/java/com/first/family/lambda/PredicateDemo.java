package com.first.family.lambda;

import java.util.function.Predicate;

/**
 * @description: PredicateDemo
 * @author: cuiweiman
 * @date: 2023/2/15 16:18
 */
public class PredicateDemo {

    public static void main(String[] args) {
        String content = "HelloPredicate";
        predicateDemo((aContent) -> aContent.contains("Hello"),
                (bContent) -> bContent.contains("Predicate"),
                content);
        predicateDemo((aContent) -> aContent.length() < 3,
                (bContent) -> bContent.contains("Predicate"),
                content);
    }

    private static void predicateDemo(Predicate<String> a, Predicate<String> b, String content) {
        // Predicate表达式 与 a && b
        boolean allEquals = a.and(b).test(content);
        System.out.println(allEquals);

        // Predicate表达式 或 a || b
        boolean anyEquals = a.or(b).test(content);
        System.out.println(anyEquals);

        // Predicate表达式 非 !a
        boolean test = a.negate().test(content);
        System.out.println(test);
    }


}
