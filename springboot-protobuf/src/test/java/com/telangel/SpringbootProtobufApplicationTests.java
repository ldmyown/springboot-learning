package com.telangel;

import com.telangel.protobuf.User;
import com.telangel.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
			System.out.println(userResponse.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
