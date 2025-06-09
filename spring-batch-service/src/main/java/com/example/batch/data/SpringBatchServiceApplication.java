package com.example.batch.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
@EnableScheduling
public class SpringBatchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchServiceApplication.class, args);
	}
}
