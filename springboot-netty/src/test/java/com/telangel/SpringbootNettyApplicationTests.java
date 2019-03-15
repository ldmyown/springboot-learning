package com.telangel;

import com.telangel.client.UpLoadClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootNettyApplicationTests {

	@Test
	public void contextLoads() throws Exception {
		UpLoadClient upLoadClient = new UpLoadClient("127.0.0.1", 8088);
		upLoadClient.uploadFile("D:/data0/cn_windows_10_multiple_editions_version_1511_x64_dvd_7223622.iso");
	}

}
