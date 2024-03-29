package com.bookstore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bookstore.config","com.bookstore.controller","com.bookstore.dao","com.bookstore.service","com.bookstore.repository","com.bookstore.controller","com.bookstore.utils"})
@OpenAPIDefinition(info = @Info(title = "BookStore API",version = "1.0",description = "API Documentation of BookStore Management System"))
@EnableScheduling
public class BookManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookManagementSystemApplication.class, args);
	}

}
