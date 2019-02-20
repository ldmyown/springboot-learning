package com.telangel.springbootredis.domain.entity;

/**
 * @version 1.0.0
 * @项目名称： springboot-redis
 * @类名称： User
 * @类描述：
 * @author： lid
 * @date： 2019/2/15 16:08
 */
public class User {

    private String name;

    private Integer age;

    private String sex;

    public User(){
    }

    public User(String name, Integer age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
