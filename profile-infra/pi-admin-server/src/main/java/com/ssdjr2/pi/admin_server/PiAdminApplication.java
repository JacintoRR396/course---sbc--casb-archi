package com.ssdjr2.pi.admin_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@SpringBootApplication
public class PiAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiAdminApplication.class, args);
	}

}
