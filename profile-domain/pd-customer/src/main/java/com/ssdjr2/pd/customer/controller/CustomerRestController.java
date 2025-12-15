package com.ssdjr2.pd.customer.controller;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssdjr2.pd.customer.exception.BussinesRuleException;
import com.ssdjr2.pd.customer.respository.CustomerRepository;
import com.ssdjr2.pd.customer.respository.entity.Customer;
import com.ssdjr2.pd.customer.service.BussinesConsumerService;

import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;

/**
 * @author jacrolrod
 * @version 1.0
 */
@RestController
@RequestMapping("/customers")
@RefreshScope
@AllArgsConstructor
public class CustomerRestController {

	private final Environment env;

	private final CustomerRepository customerRepo;

	private final BussinesConsumerService bussinesConsumerService;

	@GetMapping("/tools/check-profile")
	public String checkProfile() {
		return "The loaded environment is -> " + this.env.getProperty("spring.application.name") + " - "
				+ this.env.getProperty("server.port") + " : " + this.env.getProperty("custom.profile.active");
	}

	@GetMapping()
	public ResponseEntity<?> getAll(@QueryParam("showStatusNoContent") final boolean showStatusNoContent) {
		List<Customer> customersDB = this.customerRepo.findAll();
		if (!customersDB.isEmpty() || !showStatusNoContent) {
			return new ResponseEntity<>(customersDB, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
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
	public ResponseEntity<?> post(@RequestBody Customer customerReq)
			throws UnknownHostException, BussinesRuleException {
		this.bussinesConsumerService.setProdsToOneCustomer(customerReq);
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
	public ResponseEntity<?> getByCode(@RequestParam final String code) {
		Optional<Customer> customerDBOpt = this.customerRepo.findByCode(code);
		if (customerDBOpt.isPresent()) {
			Customer customerDB = customerDBOpt.get();
			this.bussinesConsumerService.updateProdsToOneCustomer(customerDB);

			return new ResponseEntity<>(customerDB, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
