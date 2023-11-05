package com.pablo.fashionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@PropertySource("classpath:external-config.properties")
@SpringBootApplication
public class FashionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionApiApplication.class, args);
	}

}
