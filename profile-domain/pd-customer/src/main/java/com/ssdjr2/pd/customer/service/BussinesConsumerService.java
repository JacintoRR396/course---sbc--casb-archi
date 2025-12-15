package com.ssdjr2.pd.customer.service;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssdjr2.pd.customer.exception.BussinesRuleException;
import com.ssdjr2.pd.customer.respository.entity.Customer;
import com.ssdjr2.pd.customer.respository.entity.CustomerProduct;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Service
public class BussinesConsumerService {

	private final Environment env;

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

	public BussinesConsumerService(final Environment env, final WebClient.Builder webClientBuilder) {
		this.env = env;
		this.webClientBuilder = webClientBuilder;
	}

	public void setProdsToOneCustomer(Customer customer) throws BussinesRuleException, UnknownHostException {
		List<CustomerProduct> products = customer.getProducts();
		if (Objects.nonNull(products) && !products.isEmpty()) {
			for (Iterator<CustomerProduct> it = products.iterator(); it.hasNext();) {
				CustomerProduct prod = it.next();
				String productNameMS = this.getProdNameById(prod.getProductId());
				if (productNameMS.isBlank()) {
					throw new BussinesRuleException("1025",
							"Error validacion, producto con id " + prod.getProductId() + " no existe",
							HttpStatus.PRECONDITION_FAILED);
				} else {
					prod.setCustomer(customer);
				}
			}
		}
	}

	public void updateProdsToOneCustomer(Customer customer) {
		List<CustomerProduct> products = customer.getProducts();

		products.forEach(prod -> {
			try {
				String productNameMS = this.getProdNameById(prod.getProductId());
				prod.setProductName(productNameMS);
			} catch (UnknownHostException ex) {
				Logger.getLogger(BussinesConsumerService.class.getName()).log(Level.SEVERE, null, ex);
			}
		});

		List<?> transactions = this.getAllTransactionsByIban(customer.getIban());
		customer.setTransactions(transactions);
	}

	/**
	 * Call MS Product, find a product by Id and return it name.
	 *
	 * @param id of product to find
	 * @return name of product if it was find
	 * @throws UnknownHostException
	 */
	private String getProdNameById(final Long id) throws UnknownHostException {
		String pathProdMs = this.env.getProperty("custom.consumer.product.path");
		String name = "";

		try {
			WebClient webClientBuild = this.webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
					.baseUrl(pathProdMs).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.defaultUriVariables(Collections.singletonMap("url", pathProdMs)).build();

			JsonNode block = webClientBuild.method(HttpMethod.GET).uri("/" + id).retrieve().bodyToMono(JsonNode.class)
					.block();
			
			name = block.get("name").asText();
		} catch (WebClientResponseException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				return "";
			} else {
				throw new UnknownHostException(ex.getMessage());
			}
		}

		return name;
	}

	/**
	 * Call MS Transaction, find all transaction that belong to the account give
	 *
	 * @param iban account number of the customer
	 * @return all transaction that belong this account
	 */
	private List<?> getAllTransactionsByIban(final String iban) {
		String pathTransMs = this.env.getProperty("custom.consumer.transaction.path");

		WebClient webClientBuild = this.webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
				.baseUrl(pathTransMs).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

		Optional<List<?>> transactionsOpt = Optional.ofNullable(webClientBuild.method(HttpMethod.GET)
				.uri(uriBuilder -> uriBuilder.path("/customer/transactions").queryParam("ibanAccount", iban).build())
				.retrieve().bodyToFlux(Object.class).collectList().block());

		return transactionsOpt.orElse(Collections.emptyList());
	}
}
