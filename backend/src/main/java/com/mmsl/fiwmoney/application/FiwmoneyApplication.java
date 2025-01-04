package com.mmsl.fiwmoney.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FiwmoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiwmoneyApplication.class, args);

		Application app = new Application();
		app.display();
	}

}
