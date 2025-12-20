package com.ssdjr2.pd.customer.config.docs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ssdjr2.pd.customer.config.properties.OpenApiProperties;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

	private final OpenApiProperties openApiProperties;

	@Bean
	OpenAPI myOpenAPI() {
		Server localServer = new Server();
		localServer.setUrl(this.openApiProperties.getMicro().get("urlLocal"));
		localServer.setDescription("Server URL in Local environment");

		Server devServer = new Server();
		devServer.setUrl(this.openApiProperties.getMicro().get("urlDev"));
		devServer.setDescription("Server URL in Development environment");

		Contact contact = new Contact();
		contact.setName(this.openApiProperties.getAuthor().get("name"));
		contact.setEmail(this.openApiProperties.getAuthor().get("email"));
		contact.setUrl(this.openApiProperties.getAuthor().get("url"));

		License mitLicense = new License().name(this.openApiProperties.getLicense().get("name"))
				.url(this.openApiProperties.getLicense().get("url"));

		Info info = new Info().title(this.openApiProperties.getTitle())
				.version(this.openApiProperties.getVersion())
				.description(this.openApiProperties.getDescription())
				.termsOfService(this.openApiProperties.getTermsOfService())
				.license(mitLicense)
				.contact(contact);

		return new OpenAPI().info(info).servers(List.of(localServer, devServer));
	}
}
