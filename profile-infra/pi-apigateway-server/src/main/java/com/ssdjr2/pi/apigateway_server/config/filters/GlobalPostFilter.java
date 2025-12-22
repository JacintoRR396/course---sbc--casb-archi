package com.ssdjr2.pi.apigateway_server.config.filters;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Configuration
public class GlobalPostFilter {

	@Bean
	GlobalFilter postGlobalFilter() {
		return (exchange, chain) -> chain.filter(exchange);
	}
}
