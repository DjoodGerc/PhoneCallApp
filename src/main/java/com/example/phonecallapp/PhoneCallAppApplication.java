package com.example.phonecallapp;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication()
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class PhoneCallAppApplication {
	public static void main(String[] args) {

		SpringApplication.run(PhoneCallAppApplication.class, args);

	}

}
