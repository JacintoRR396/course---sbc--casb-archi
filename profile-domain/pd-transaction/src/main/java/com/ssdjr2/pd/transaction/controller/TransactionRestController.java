package com.ssdjr2.pd.transaction.controller;

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

import com.ssdjr2.pd.transaction.entities.Transaction;
import com.ssdjr2.pd.transaction.respository.TransactionRepository;

import lombok.AllArgsConstructor;

/**
 * @author jacrolrod
 * @version 1.0
 */
@RestController
@RequestMapping("/transactions")
@RefreshScope
@AllArgsConstructor
public class TransactionRestController {

	private final Environment env;

	private final TransactionRepository transactionRepo;

	@GetMapping("/tools/check-profile")
	public String checkProfile() {
		return "The loaded environment is -> " + this.env.getProperty("spring.application.name") + " - "
				+ this.env.getProperty("server.port") + " : " + this.env.getProperty("custom.profile.active");
	}

	@GetMapping()
	public ResponseEntity<?> getAll() {
		List<Transaction> transesDB = this.transactionRepo.findAll();

		return new ResponseEntity<>(transesDB, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") final Long id) {
		return this.transactionRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/customer/transactions")
	public ResponseEntity<?> getByIban(@RequestParam final String ibanAccount) {
		return new ResponseEntity<>(this.transactionRepo.findByIbanAccount(ibanAccount), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody final Transaction transactionReq) {
		Transaction transDB = this.transactionRepo.save(transactionReq);

		return new ResponseEntity<>(transDB, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable("id") final Long id, @RequestBody final Transaction transactionReq) {
		Optional<Transaction> transDBOpt = this.transactionRepo.findById(id);
		if (transDBOpt.isPresent()) {
			Transaction transDB = transDBOpt.get();
			transDB.setReference(transactionReq.getReference());
			transDB.setIbanAccount(transactionReq.getIbanAccount());
			transDB.setChannel(transactionReq.getChannel());
			transDB.setStatus(transactionReq.getStatus());
			transDB.setAmount(transactionReq.getAmount());
			transDB.setFee(transactionReq.getFee());
			transDB.setDescription(transactionReq.getDescription());
			transDB.setDate(transactionReq.getDate());

			return new ResponseEntity<>(this.transactionRepo.save(transDB), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") final Long id) {
		Optional<Transaction> transDBOpt = this.transactionRepo.findById(id);
		if (transDBOpt.isPresent()) {
			Transaction transDB = transDBOpt.get();
			this.transactionRepo.delete(transDB);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
