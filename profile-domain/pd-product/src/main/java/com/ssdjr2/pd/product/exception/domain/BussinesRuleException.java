package com.ssdjr2.pd.product.exception.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BussinesRuleException extends RuntimeException {

	private static final long serialVersionUID = 421048234394959464L;

	private Long id;
	private String code;
	private HttpStatus httpStatus;

	public BussinesRuleException(final Long id, final String code, final String message, final HttpStatus httpStatus) {
		super(message);
		this.id = id;
		this.code = code;
		this.httpStatus = httpStatus;
	}

	public BussinesRuleException(final String code, final String message, final HttpStatus httpStatus) {
		super(message);
		this.code = code;
		this.httpStatus = httpStatus;
	}

	public BussinesRuleException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
