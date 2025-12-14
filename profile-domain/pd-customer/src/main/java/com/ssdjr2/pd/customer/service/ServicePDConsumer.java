package com.ssdjr2.pd.customer.service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Service
public class ServicePDConsumer {
	
	private static final String PROD_MS_PATH = "http://MS-PD-PRODUCT/rest/v1/products";
	private static final String TRANS_MS_PATH = "http://MS-PD-TRANSACTION/rest/v1/transactions";
	
	private final WebClient.Builder webClientBuilder;

	HttpClient client = HttpClient.create()
			// Connection Timeout: is a period within which a connection between a client
			// and a server must be established
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000).option(ChannelOption.SO_KEEPALIVE, true)
			.option(EpollChannelOption.TCP_KEEPIDLE, 300).option(EpollChannelOption.TCP_KEEPINTVL, 60)
			// Response Timeout: The maximun time we wait to receive a response after
			// sending a request
			.responseTimeout(Duration.ofSeconds(1))
			// Read and Write Timeout: A read timeout occurs when no data was read within a
			// certain period of time, while the write timeout when a write operation cannot
			// finish at a specific time
			.doOnConnected(connection -> {
				connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
				connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
			});
	
	public ServicePDConsumer(WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}
	
	/**
	 * Call MS Product, find a product by Id and return it name.
	 *
	 * @param id of product to find
	 * @return name of product if it was find
	 */
	public String getProdNameById(Long id) {
		WebClient webClientBuild = this.webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
				.baseUrl(PROD_MS_PATH).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultUriVariables(Collections.singletonMap("url", PROD_MS_PATH)).build();

		JsonNode block = webClientBuild.method(HttpMethod.GET).uri("/" + id).retrieve().bodyToMono(JsonNode.class)
				.block();
		String name = block.get("name").asText();

		return name;
	}

	/**
	 * Call MS Transaction, find all transaction that belong to the account give
	 *
	 * @param iban account number of the customer
	 * @return all transaction that belong this account
	 */
	public List<?> getAllTransactionsByIban(String iban) {
		WebClient webClientBuild = this.webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
				.baseUrl(TRANS_MS_PATH).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();

		Optional<List<?>> transactionsOpt = Optional.ofNullable(webClientBuild.method(HttpMethod.GET)
				.uri(uriBuilder -> uriBuilder.path("/customer/transactions").queryParam("ibanAccount", iban).build())
				.retrieve().bodyToFlux(Object.class).collectList().block());

		return transactionsOpt.orElse(Collections.emptyList());
	}
}
