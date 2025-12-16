package com.ssdjr2.pd.transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Configuration
public class AppConfig {

	@Bean
	OpenAPI customOpenAPI() {
		Info infoAPI = new Info().version("1.0.0").title("Transaction API")
				.description("This API manages the CRUD operations with respect to transactions.");

		return new OpenAPI().components(new Components()).info(infoAPI);
	}
}
