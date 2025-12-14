package com.ssdjr2.pd.product.controller;

import java.util.List;
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
@RequestMapping("/products")
@AllArgsConstructor
public class ProductRestController {
	
	private final Environment env;

	private final ProductRepository prodRepo;
	
	@GetMapping("/tools/check-profile")
	public String checkProfile() {
		return "The loaded environment is -> " + this.env.getProperty("spring.application.name") + " - "
				+ this.env.getProperty("server.port") + " : " + this.env.getProperty("custom.profile.active");
	}

	@GetMapping()
	public ResponseEntity<?> getAll() {
		List<Product> prodsDB = this.prodRepo.findAll();
		
		return new ResponseEntity<>(prodsDB, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") final Long id) {
		Optional<Product> prodDBOpt = this.prodRepo.findById(id);
		if (prodDBOpt.isPresent()) {
			Product prodDB = prodDBOpt.get();
			
			return new ResponseEntity<>(prodDB, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody final Product prodReq) {
		Product prodDB = this.prodRepo.save(prodReq);
		
		return new ResponseEntity<>(prodDB, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable("id") final Long id, @RequestBody final Product prodReq) {
		Optional<Product> prodDBOpt = this.prodRepo.findById(id);
		if (prodDBOpt.isPresent()) {
			Product productDB = prodDBOpt.get();
			productDB.setCode(prodReq.getCode());
			productDB.setName(prodReq.getName());

			return new ResponseEntity<>(this.prodRepo.save(productDB), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") final Long id) {
		Optional<Product> prodDBOpt = this.prodRepo.findById(id);
		if (prodDBOpt.isPresent()) {
			Product prodDB = prodDBOpt.get();
			this.prodRepo.delete(prodDB);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
