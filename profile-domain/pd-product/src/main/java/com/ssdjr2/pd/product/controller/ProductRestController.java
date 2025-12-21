package com.ssdjr2.pd.product.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssdjr2.pd.product.domain.dto.ProductReqDTO;
import com.ssdjr2.pd.product.domain.dto.ProductRespDTO;
import com.ssdjr2.pd.product.interceptor.LogExec;
import com.ssdjr2.pd.product.service.ProductService;

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
@Tag(name = "Product", description = "This section serve all functionality for management products")
@RestController
@RequestMapping("/products")
@RefreshScope
@AllArgsConstructor
public class ProductRestController {

	private final Environment env;

	private final ProductService productService;

	@Operation(description = "Check the active profile", summary = "Show app-name, port and profile")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/tools/check-profile")
	public String checkProfile() {
		return "The loaded environment is -> " + this.env.getProperty("spring.application.name") + " - "
				+ this.env.getProperty("server.port") + " : " + this.env.getProperty("custom.profile.active");
	}

	@Operation(description = "Return all products bundled into Response")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductRespDTO.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@LogExec
	@GetMapping()
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(this.productService.getAll(), HttpStatus.OK);
	}

	@Operation(description = "Return one product by its id into Response", summary = "Return 404 if no data found")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProductRespDTO.class))),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@LogExec
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") final Long id) {
		return this.productService.getById(id)
				.map(productRespDTO -> new ResponseEntity<>(productRespDTO, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@Operation(description = "Return the product created into Response")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ProductRespDTO.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@LogExec
	@PostMapping
	public ResponseEntity<?> post(@RequestBody final ProductReqDTO produdctReqDTO) {
		return new ResponseEntity<>(this.productService.add(produdctReqDTO), HttpStatus.CREATED);
	}

	@Operation(description = "Return the product updated into Response", summary = "Return 404 if no data found")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProductRespDTO.class))),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@LogExec
	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable("id") final Long id, @RequestBody final ProductReqDTO produdctReqDTO) {
		return this.productService.update(id, produdctReqDTO)
				.map(productRespDTO -> new ResponseEntity<>(productRespDTO, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@Operation(description = "Delete one product by its id", summary = "Return 404 if no data found")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "No content"),
			@ApiResponse(responseCode = "404", description = "Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@LogExec
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") final Long id) {
		boolean deleted = this.productService.deleteById(id);

		return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
