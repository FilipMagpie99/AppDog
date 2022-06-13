package com.example.demo;

import com.example.demo.models.Posting;
import com.example.demo.models.User;
import com.example.demo.service.PostingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Autowired
	UserService userService;
	@Autowired
	PostingService postingService;
	@Autowired
	BCryptPasswordEncoder pwEncoder;

	@Bean
	CommandLineRunner runner() {
		return args -> {

		User admin = new User("admin", "admin", "admin", "admin", "admin@admin.com", "000000000");
		admin.setPassword(pwEncoder.encode(admin.getPassword()));
		admin.setRole("ROLE_ADMIN");
		userService.setUser(admin);

//			Posting posting = new Posting("a","a");
//			postingService.setPosting(posting);
//			Posting posting = new Posting("b","b");
//			postingService.setPosting(posting);
		};
	}
}
