package com.foodboxapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodboxApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodboxApiApplication.class, args);
		System.out.println("FoodBox api works!");
	}

}
