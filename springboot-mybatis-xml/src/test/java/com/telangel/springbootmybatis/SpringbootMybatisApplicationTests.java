package com.telangel.springbootmybatis;

import com.telangel.springbootmybatis.entity.FileProp;
import com.telangel.springbootmybatis.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {

	@Autowired
	FileService fileService;

	@Test
	public void contextLoads() {

		// 列出文件夹中所有的文件
		File file = new File("D:/Program Files (x86)");
		saveFile(file);
	}

	void saveFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file1 : files) {
				saveFile(file1);
			}
		} else {
			FileProp fileProp = new FileProp();
			fileProp.setFileName(file.getName());
			fileProp.setFilePath(file.getPath());
			fileProp.setFileSize(file.length());
			fileProp.setFileStatus(10);
			fileService.addFile(fileProp);
		}
	}

}
