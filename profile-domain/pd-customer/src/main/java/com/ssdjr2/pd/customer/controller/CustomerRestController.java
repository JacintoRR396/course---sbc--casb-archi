package com.ssdjr2.pd.customer.controller;

import java.net.UnknownHostException;

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

import com.ssdjr2.pd.customer.domain.dto.CustomerReqDTO;
import com.ssdjr2.pd.customer.domain.dto.CustomerRespDTO;
import com.ssdjr2.pd.customer.exception.BussinesRuleException;
import com.ssdjr2.pd.customer.service.CustomerService;

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
@Tag(name = "Customer API", description = "This APi serve all functionality for management customers")
@RestController
@RequestMapping("/customers")
@RefreshScope
@AllArgsConstructor
public class CustomerRestController {

	private final Environment env;

	private final CustomerService customerService;

	@Operation(description = "Check the active profile", summary = "Show app-name, port and profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/tools/check-profile")
	public String checkProfile() {
		return "The loaded environment is -> " + this.env.getProperty("spring.application.name") + " - "
				+ this.env.getProperty("server.port") + " : " + this.env.getProperty("custom.profile.active");
	}

	@Operation(description = "Return all customers bundled into Response")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerRespDTO.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping()
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(this.customerService.getAll(), HttpStatus.OK);
	}

	@Operation(description = "Return one customer by its id into Response", summary = "Return 404 if no data found")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CustomerRespDTO.class))),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") final Long id) {
		return this.customerService.getById(id)
				.map(customerRespDTO -> new ResponseEntity<>(customerRespDTO, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@Operation(description = "Return one customer by its code into Response", summary = "Return 404 if no data found")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CustomerRespDTO.class))),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/full-by-code")
	public ResponseEntity<?> getByCode(@RequestParam("code") final String code) {
		return this.customerService.getByCode(code)
				.map(customerRespDTO -> new ResponseEntity<>(customerRespDTO, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@Operation(description = "Return the customer created into Response")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CustomerRespDTO.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PostMapping
	public ResponseEntity<?> post(@RequestBody CustomerReqDTO customerReqDTO)
			throws UnknownHostException, BussinesRuleException {
		return new ResponseEntity<>(this.customerService.add(customerReqDTO), HttpStatus.CREATED);
	}

	@Operation(description = "Return the customer updated into Response", summary = "Return 404 if no data found")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CustomerRespDTO.class))),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable("id") final Long id, @RequestBody final CustomerReqDTO customerReqDTO) {
		return this.customerService
				.update(id, customerReqDTO)
				.map(customerRespDTO -> new ResponseEntity<>(customerRespDTO, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@Operation(description = "Delete one customer by its id", summary = "Return 404 if no data found")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
		boolean deleted = this.customerService.deleteById(id);

		return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
