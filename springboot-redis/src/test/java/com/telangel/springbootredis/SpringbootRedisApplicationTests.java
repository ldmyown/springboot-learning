package com.telangel.springbootredis;

import com.alibaba.fastjson.JSONObject;
import com.telangel.springbootredis.domain.UserRedis;
import com.telangel.springbootredis.domain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisApplicationTests {

	@Autowired
	private UserRedis userRedis;

	@Test
	public void add() {
		User user = new User("aa", 10, "男"	);
		userRedis.add("1",60l, user);
		if (userRedis.get("1") != null) {
			System.out.println("保存成功，保存的数据为" + JSONObject.toJSONString(userRedis.get("1")));
		}
	}

	@Test
	public void addList() {
		User user1 = new User("aa", 10, "男");
		User user2 = new User("aa", 10, "男");
		userRedis.add("2",60l, Arrays.asList(user1, user2));
		if (userRedis.getList("2") != null) {
			System.out.println("保存成功，保存的数据为" + JSONObject.toJSONString(userRedis.getList("2")));
		}
	}

	@Test
	public void delete(){
		userRedis.delete("2");

	}

}

