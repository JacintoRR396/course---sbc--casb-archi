package com.ssdjr2.pd.product.controller;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssdjr2.pd.product.respository.ProductRepository;
import com.ssdjr2.pd.product.respository.entity.Product;

import lombok.AllArgsConstructor;

/**
 * @author jacrolrod
 * @version 1.0
 */
@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductRestController {
	
	private final Environment env;

	private final ProductRepository productRepo;
	
	@GetMapping("/tools/check-profile")
	public String checkProfile() {
		return "The loaded environment is : " + this.env.getProperty("custom.activeprofileName") + " - " + this.env.getProperty("server.port");
	}

	@GetMapping()
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(this.productRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable final long id) {
		Optional<Product> customerOpt = this.productRepo.findById(id);
		if (customerOpt.isPresent()) {
			return new ResponseEntity<>(customerOpt.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody final Product customerReq) {
		return new ResponseEntity<>(this.productRepo.save(customerReq), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable final long id, @RequestBody final Product customerReq) {
		Optional<Product> customerOpt = this.productRepo.findById(id);
		if (customerOpt.isPresent()) {
			Product customerDB = customerOpt.get();
			customerDB.setCode(customerReq.getCode());
			customerDB.setName(customerReq.getName());

			return new ResponseEntity<>(this.productRepo.save(customerDB), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable final long id) {
		Optional<Product> customerOpt = this.productRepo.findById(id);
		if (customerOpt.isPresent()) {
			this.productRepo.delete(customerOpt.get());
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
