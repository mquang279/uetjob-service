package com.example.demo;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.service.StorageService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.directory("./")
				.filename(".env")
				.load();

		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}
}
