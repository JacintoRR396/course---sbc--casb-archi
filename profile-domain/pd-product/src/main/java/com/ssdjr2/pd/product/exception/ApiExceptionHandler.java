package com.ssdjr2.pd.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssdjr2.pd.product.domain.StandarizedApiExResp;

/**
 * @author jacrolrod
 * @version 1.0
 */
@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUnknownHostException(Exception ex) {
		StandarizedApiExResp standarizedApiExResp = new StandarizedApiExResp("TECH", "Input / Ouput error", "1024",
				ex.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standarizedApiExResp);
	}

	@ExceptionHandler(BussinesRuleException.class)
	public ResponseEntity<?> handleBussinesRuleException(BussinesRuleException ex) {
		StandarizedApiExResp standarizedApiExResp = new StandarizedApiExResp("BUSSINES", "Validation error",
				ex.getCode(), ex.getMessage());

		return ResponseEntity.status(ex.getHttpStatus()).body(standarizedApiExResp);
	}
}
