package com.telangel;

import com.telangel.protobuf.User;
import com.telangel.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootProtobufApplicationTests {

	@Test
	public void contextLoads() {
		try {
			URI uri = new URI("http", null, "127.0.0.1", 8080, "/getUser", "", null);
			HttpPost request = new HttpPost(uri);
			User.UserRequest.Builder builder = User.UserRequest.newBuilder();
			builder.setUsername("aa");
			HttpResponse response = HttpUtils.doPost(request, builder.build());
			User.UserResponse userResponse = User.UserResponse.parseFrom(response.getEntity().getContent());
			System.out.println(userResponse.getDept());
			System.out.println(userResponse.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void test() throws IOException {
		// 按照定义的数据结构，创建一个对象
		User.UserResponse.Builder userInfo = User.UserResponse.newBuilder();
		userInfo.setUsername("张三");
		userInfo.setAge(10);
		userInfo.setDept("人事部");
		userInfo.setJob("招聘");

		// 将数据写到输出流
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		User.UserResponse userResponse = userInfo.build();
		userResponse.writeTo(output);
		// 将数据序列化后发送
		byte[] bytes = output.toByteArray();
		// 接收到流并读取
		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		// 反序列化  
		User.UserResponse userResponse2 = User.UserResponse.parseFrom(input);

		System.out.println("name:" + userResponse2.getUsername());
		System.out.println("age:" + userResponse2.getAge());
		System.out.println("dept:" + userResponse2.getDept());
		System.out.println("job:" + userResponse2.getJob());

	}

}
