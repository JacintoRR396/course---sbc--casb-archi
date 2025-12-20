package com.ssdjr2.pd.product.config.docs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ssdjr2.pd.product.config.properties.OpenApiProperties;

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
		localServer.setUrl(this.openApiProperties.getMicro().get(OpenApiProperties.SERVER_URL_LOCAL));
		localServer.setDescription(this.getServerUrlDescription("Local"));

		Server devServer = new Server();
		devServer.setUrl(this.openApiProperties.getMicro().get(OpenApiProperties.SERVER_URL_DEV));
		devServer.setDescription(this.getServerUrlDescription("Dev"));

		Contact contact = new Contact();
		contact.setName(this.openApiProperties.getAuthor().get(OpenApiProperties.AUTHOR_NAME));
		contact.setEmail(this.openApiProperties.getAuthor().get(OpenApiProperties.AUTHOR_EMAIL));
		contact.setUrl(this.openApiProperties.getAuthor().get(OpenApiProperties.AUTHOR_URL));

		License mitLicense = new License().name(this.openApiProperties.getLicense().get(OpenApiProperties.LICENSE_NAME))
				.url(this.openApiProperties.getLicense().get(OpenApiProperties.LICENSE_URL));

		Info info = new Info().title(this.openApiProperties.getTitle()).version(this.openApiProperties.getVersion())
				.description(this.openApiProperties.getDescription())
				.termsOfService(this.openApiProperties.getTermsOfService()).license(mitLicense).contact(contact);

		return new OpenAPI().info(info).servers(List.of(localServer, devServer));
	}

	private String getServerUrlDescription(String server) {
		return "Server URL in " + server + " environment";
	}
}
