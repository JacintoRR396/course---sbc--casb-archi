package com.ssdjr2.pd.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssdjr2.pd.product.exception.domain.BussinesRuleException;
import com.ssdjr2.pd.product.exception.dto.ExceptionErrorRespDTO;

/**
 * @author jacrolrod
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUnknownHostException(Exception ex) {
		ExceptionErrorRespDTO exceptionErrorRespDTO = new ExceptionErrorRespDTO("TECH", "Input / Ouput error", "1024",
				ex.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionErrorRespDTO);
	}

	@ExceptionHandler(BussinesRuleException.class)
	public ResponseEntity<?> handleBussinesRuleException(BussinesRuleException ex) {
		ExceptionErrorRespDTO exceptionErrorRespDTO = new ExceptionErrorRespDTO("BUSSINES", "Validation error",
				ex.getCode(), ex.getMessage());

		return ResponseEntity.status(ex.getHttpStatus()).body(exceptionErrorRespDTO);
	}
}
