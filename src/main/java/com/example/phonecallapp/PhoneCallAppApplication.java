package com.example.phonecallapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class PhoneCallAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneCallAppApplication.class, args);
	}

}
