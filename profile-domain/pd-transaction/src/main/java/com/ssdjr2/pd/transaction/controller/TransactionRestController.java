package com.ssdjr2.pd.transaction.controller;

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

import com.ssdjr2.pd.transaction.domain.dto.TransactionReqDTO;
import com.ssdjr2.pd.transaction.domain.dto.TransactionRespDTO;
import com.ssdjr2.pd.transaction.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Tag(name = "Transaction API", description = "This APi serve all functionality for management transactions")
@RestController
@RequestMapping("/transactions")
@RefreshScope
@AllArgsConstructor
public class TransactionRestController {

	private final Environment env;

	private final TransactionService transactionService;

	@Operation(description = "Check the active profile", summary = "Show app-name, port and profile")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/tools/check-profile")
	public String checkProfile() {
		return "The loaded environment is -> " + this.env.getProperty("spring.application.name") + " - "
				+ this.env.getProperty("server.port") + " : " + this.env.getProperty("custom.profile.active");
	}

	@Operation(description = "Return all transactions bundled into Response")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionRespDTO.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping()
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(this.transactionService.getAll(), HttpStatus.OK);
	}

	@Operation(description = "Return all transactions bundled by its iban into Response")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionRespDTO.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/customer")
	public ResponseEntity<?> getAllByIban(@RequestParam("ibanAccount") final String ibanAccount) {
		return new ResponseEntity<>(this.transactionService.getAllByIban(ibanAccount), HttpStatus.OK);
	}

	@Operation(description = "Return one transaction by its id into Response", summary = "Return 404 if no data found")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TransactionRespDTO.class))),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") final Long id) {
		return this.transactionService.getById(id)
				.map(productRespDTO -> new ResponseEntity<>(productRespDTO, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@Operation(description = "Return the transaction created into Response")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = TransactionRespDTO.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PostMapping
	public ResponseEntity<?> post(@RequestBody final TransactionReqDTO transactionReqDTO) {
		return new ResponseEntity<>(this.transactionService.add(transactionReqDTO), HttpStatus.CREATED);
	}

	@Operation(description = "Return the transaction updated into Response", summary = "Return 404 if no data found")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TransactionRespDTO.class))),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable("id") final Long id,
			@RequestBody final TransactionReqDTO transactionReqDTO) {
		return this.transactionService.update(id, transactionReqDTO)
				.map(productRespDTO -> new ResponseEntity<>(productRespDTO, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@Operation(description = "Delete one transaction by its id", summary = "Return 404 if no data found")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "No content"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") final Long id) {
		boolean deleted = this.transactionService.deleteById(id);

		return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
