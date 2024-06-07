package com.example.Devops_lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DevopsLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevopsLabApplication.class, args);
	}
	@GetMapping("/")
	public String hello() {
		return "Hello world";
	}


}
