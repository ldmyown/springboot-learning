package com.telangel;

import com.telangel.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootFileuploadWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootFileuploadWebApplication.class, args);
	}

	@Bean
	CommandLineRunner init(final StorageService storageService) {
		return  (args) -> {
			storageService.deleteAll();
			storageService.init();
		} ;
	}

}
