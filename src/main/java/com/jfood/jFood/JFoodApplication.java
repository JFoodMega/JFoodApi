package com.jfood.jFood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.jfood.jFood.entity")
@EnableJpaRepositories("com.jfood.jFood.repository")
public class JFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(JFoodApplication.class, args);
	}

}
