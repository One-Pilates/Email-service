package com.onepilates.email_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@SpringBootApplication
@EnableRabbit
public class EmailService {

	public static void main(String[] args) {
		SpringApplication.run(EmailService.class, args);
	}

}
