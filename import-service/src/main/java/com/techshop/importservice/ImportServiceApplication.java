package com.techshop.importservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(
		basePackages = "com.techshop.clients"
)
public class ImportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImportServiceApplication.class, args);
	}

}
