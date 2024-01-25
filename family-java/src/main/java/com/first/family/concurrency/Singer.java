package com.first.family.concurrency;

import java.util.Arrays;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/12/21 18:06
 */
public class Singer {

    public String name = "鱼蛋";
    protected String sex = "女";
    private Integer age = 18;
    public Integer[] years = {2021, 2022, 2023};

    @Override
    public String toString() {
        return "Singer{" +
                "name=\"" + name + '\"' +
                ", sex=\"" + sex + '\"' +
                ", age=\"" + age + '\"' +
                ", years=" + Arrays.toString(years) +
                '}';
    }
}
