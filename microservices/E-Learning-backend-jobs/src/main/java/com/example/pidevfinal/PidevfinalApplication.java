package com.example.pidevfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PidevfinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PidevfinalApplication.class, args);
	}

}
