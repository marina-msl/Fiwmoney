package com.mmsl.fiwmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.mmsl.fiwmoney")
@EnableJpaRepositories(basePackages = "com.mmsl.fiwmoney.repository")
public class FiwmoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiwmoneyApplication.class, args);
 	}

}