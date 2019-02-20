package com.telangel.springbootmybatis.entity;

/**
 * @version 1.0.0
 * @项目名称： springboot-mybatis
 * @类名称： User
 * @类描述：
 * @author： lid
 * @date： 2019/2/20 13:56
 */
public class User {

    private Long userId;

    private String userName;

    private String userPassword;

    private Integer sex;

    private Integer age;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
