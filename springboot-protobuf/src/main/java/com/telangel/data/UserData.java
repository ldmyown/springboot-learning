package com.telangel.data;

import com.telangel.protobuf.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0.0
 * @项目名称： springboot-protobuf
 * @类名称： UserData
 * @类描述：
 * @author： lid
 * @date： 2019/3/12 18:20
 */
@Service
public class UserData {

    public Map<String, User.UserResponse> getUserData() {
        Map<String, User.UserResponse> userM = new ConcurrentHashMap<>();
        Arrays.asList(
                user("aa", 10, "技术", "开发"),
                user("bb", 20, "人事", "招聘"),
                user("cc", 30, "人事", "招聘"),
                user("dd", 20, "技术", "实施"),
                user("ee", 40, "办公室", "boss")
        ).forEach(c -> userM.put(c.getUsername(), c));
        return userM;
    }

    private User.UserResponse user(String username, int age, String dept, String job) {
        return User.UserResponse.newBuilder().setUsername(username).setAge(age).setDept(dept).setJob(job).build();
    }
}
