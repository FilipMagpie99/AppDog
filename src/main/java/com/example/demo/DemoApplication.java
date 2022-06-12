package com.example.demo;

import com.example.demo.models.Posting;
import com.example.demo.models.User;
import com.example.demo.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Autowired
	PostingService postingService;

	@Bean
	CommandLineRunner runner() {
		return args -> {

			Posting posting1 = new Posting("a","a","a");
            postingService.setPosting(posting1);
		};
	}
}
