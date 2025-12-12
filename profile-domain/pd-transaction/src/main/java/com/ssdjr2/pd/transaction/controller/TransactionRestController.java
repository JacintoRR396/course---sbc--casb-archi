package com.ssdjr2.pd.transaction.controller;

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
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionRestController {
	
	private final Environment env;

	private final TransactionRepository transactionRepo;
	
	@GetMapping("/tools/check-profile")
	public String checkProfile() {
		return "The loaded environment is : " + this.env.getProperty("custom.activeprofileName") + " - " + this.env.getProperty("server.port");
	}

	@GetMapping()
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(this.transactionRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable final long id) {
		return this.transactionRepo.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/customer/transactions")
	public ResponseEntity<?> get(@RequestParam final String ibanAccount) {
		return new ResponseEntity<>(this.transactionRepo.findByIbanAccount(ibanAccount), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody final Transaction transactionReq) {
		return ResponseEntity.ok(this.transactionRepo.save(transactionReq));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable final long id, @RequestBody final Transaction transactionReq) {
		Optional<Transaction> transactionOpt = this.transactionRepo.findById(id);
		if (transactionOpt.isPresent()) {
			Transaction transactionDB = transactionOpt.get();
			transactionDB.setReference(transactionReq.getReference());
			transactionDB.setIbanAccount(transactionReq.getIbanAccount());
			transactionDB.setChannel(transactionReq.getChannel());
			transactionDB.setStatus(transactionReq.getStatus());
			transactionDB.setAmount(transactionReq.getAmount());
			transactionDB.setFee(transactionReq.getFee());
			transactionDB.setDescription(transactionReq.getDescription());
			transactionDB.setDate(transactionReq.getDate());

			return new ResponseEntity<>(this.transactionRepo.save(transactionDB), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable final long id) {
		Optional<Transaction> customerOpt = this.transactionRepo.findById(id);
		if (customerOpt.isPresent()) {
			this.transactionRepo.delete(customerOpt.get());
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
