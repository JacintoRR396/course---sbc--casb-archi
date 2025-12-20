package com.ssdjr2.pd.product.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "openapi")
@Data
public class OpenApiProperties {

	public static final String LICENSE_NAME = "name";
	public static final String LICENSE_URL = "url";
	public static final String AUTHOR_NAME = "name";
	public static final String AUTHOR_EMAIL = "email";
	public static final String AUTHOR_URL = "url";
	public static final String SERVER_URL_LOCAL = "urlLocal";
	public static final String SERVER_URL_DEV = "urlDev";

	// Open API Data
	private String version;
	private String title;
	private String description;
	private String termsOfService;
	private Map<String, String> license;		// name, url

	// Personal Data
	private Map<String, String> author;		// name, email, url

	// Micro Data
	private Map<String, String> micro;		// urlLocal, urlDev
}
