package com.zurl.zurl_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.zurl.zurl_api.serviceImpl.RedisService;

@SpringBootApplication
public class ZurlApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZurlApiApplication.class, args);
	}
}

@Component
class RedisTest implements CommandLineRunner {
	
	@Autowired
	RedisService redisService;

	@Override
	public void run(String... args) throws Exception {
		redisService.set("Ping", "Pong");
		System.out.println(redisService.get("Ping", String.class));
		redisService.set("Pong", "Ping", 30L);
		System.out.println(redisService.get("Pong", String.class));
	}
}
