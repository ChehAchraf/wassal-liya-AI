package com.multitrans.wasalliya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class WasalliyaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WasalliyaApplication.class, args);
	}

}
