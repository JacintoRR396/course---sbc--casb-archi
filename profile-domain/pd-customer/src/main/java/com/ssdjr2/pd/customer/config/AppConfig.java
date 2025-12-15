package com.ssdjr2.pd.customer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class AppConfig {

	@Bean
	OpenAPI customOpenAPI() {
		Info infoAPI = new Info().version("1.0.0").title("Customer API")
				.description("This API manages the CRUD operations with respect to customers.");

		return new OpenAPI().components(new Components()).info(infoAPI);
	}

	@Bean
	@LoadBalanced
	WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}
}
