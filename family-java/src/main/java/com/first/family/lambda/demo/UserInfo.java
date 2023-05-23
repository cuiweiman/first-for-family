package com.first.family.lambda.demo;

/**
 * @description: user info
 * @author: cuiweiman
 * @date: 2023/2/15 13:15
 */
public class UserInfo {

    private String username;
    private Integer age;

    public UserInfo() {
    }

    public UserInfo(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
