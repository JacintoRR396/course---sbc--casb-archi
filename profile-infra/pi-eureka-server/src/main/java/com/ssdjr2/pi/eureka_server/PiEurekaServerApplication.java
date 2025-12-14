package com.ssdjr2.pi.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class PiEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiEurekaServerApplication.class, args);
	}

}
