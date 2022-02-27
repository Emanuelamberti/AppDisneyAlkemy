package com.appDisney.application;

import com.appDisney.application.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Autowired
	public UserService userService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
