package com.ssdjr2.pd.customer.controller;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssdjr2.pd.customer.respository.CustomerRepository;
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
@RestController
@RequestMapping("/customers")
public class CustomerRestController {

	private static final String PROD_MS_PATH = "http://localhost:8082/rest/v1/product";
	private static final String TRANS_MS_PATH = "http://localhost:8083/rest/v1/transaction";

	@Autowired
	private Environment env;

	private final WebClient.Builder webClientBuilder;
	private final CustomerRepository customerRepo;

	public CustomerRestController(WebClient.Builder webClientBuilder, CustomerRepository customerRepo) {
		this.webClientBuilder = webClientBuilder;
		this.customerRepo = customerRepo;
	}

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

	@GetMapping("/tools/check-profile")
	public String checkProfile() {
		return "The loaded environment is -> " + this.env.getProperty("spring.application.name") + " - "
				+ this.env.getProperty("server.port") + " : " + this.env.getProperty("custom.profile.active");
	}

	@GetMapping()
	public ResponseEntity<?> getAll() {
		List<Customer> customersDB = this.customerRepo.findAll();

		return new ResponseEntity<>(customersDB, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") final Long id) {
		Optional<Customer> customerDBOpt = this.customerRepo.findById(id);
		if (customerDBOpt.isPresent()) {
			Customer customerDB = customerDBOpt.get();
			
			return new ResponseEntity<>(customerDB, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody final Customer customerReq) {
		customerReq.getProducts().forEach(prod -> prod.setCustomer(customerReq));
		Customer customerDB = this.customerRepo.save(customerReq);

		return new ResponseEntity<>(customerDB, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable("id") final Long id, @RequestBody final Customer customerReq) {
		Optional<Customer> customerDBOpt = this.customerRepo.findById(id);
		if (customerDBOpt.isPresent()) {
			Customer customerDB = customerDBOpt.get();
			customerDB.setCode(customerReq.getCode());
			customerDB.setName(customerReq.getName());
			customerDB.setSurname(customerReq.getSurname());
			customerDB.setPhone(customerReq.getPhone());
			customerDB.setAddress(customerReq.getAddress());
			customerDB.setIban(customerReq.getIban());

			return new ResponseEntity<>(this.customerRepo.save(customerDB), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
		Optional<Customer> customerDBOpt = this.customerRepo.findById(id);
		if (customerDBOpt.isPresent()) {
			Customer customerDB = customerDBOpt.get();
			this.customerRepo.delete(customerDB);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/full-by-code")
	public ResponseEntity<?> getByCode(@RequestParam String code) {
		Optional<Customer> customerDBOpt = this.customerRepo.findByCode(code);
		if (customerDBOpt.isPresent()) {
			Customer customerDB = customerDBOpt.get();
			List<CustomerProduct> productsDB = customerDB.getProducts();
			productsDB.forEach(prod -> {
				String productNameMS = this.getProdNameMS(prod.getProductId());
				prod.setProductName(productNameMS);
			});

			// find all transactions that belong this account number
			List<?> transactions = this.getTransactionsMS(customerDB.getIban());
			customerDB.setTransactions(transactions);

			return new ResponseEntity<>(customerDB, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Call MS Product, find a product by Id and return it name.
	 *
	 * @param id of product to find
	 * @return name of product if it was find
	 */
	private String getProdNameMS(Long id) {
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
	private List<?> getTransactionsMS(String iban) {
		WebClient webClientBuild = this.webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
				.baseUrl(TRANS_MS_PATH).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();

		Optional<List<?>> transactionsOpt = Optional.ofNullable(webClientBuild.method(HttpMethod.GET)
				.uri(uriBuilder -> uriBuilder.path("/customer/transactions").queryParam("ibanAccount", iban).build())
				.retrieve().bodyToFlux(Object.class).collectList().block());

		return transactionsOpt.orElse(Collections.emptyList());
	}
}
