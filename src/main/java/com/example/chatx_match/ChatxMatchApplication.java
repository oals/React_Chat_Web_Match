package com.example.chatx_match;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.example.chatx_match",
		"com.example.auth_common" //공통 인증 모듈 (인터셉터, 리졸버, webConfig)
	}
)
public class ChatxMatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChatxMatchApplication.class, args);
	}

}
