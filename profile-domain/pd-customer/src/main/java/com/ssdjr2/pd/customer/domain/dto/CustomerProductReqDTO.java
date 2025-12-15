package com.ssdjr2.pd.customer.domain.dto;

import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Schema(name = "CustomerProductReqDTO", description = "DTO than represent a request about one product of a customer")
@Data
public class CustomerProductReqDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 2512738374682604780L;

	@Schema(name = "product_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1", defaultValue = "0", description = "Unique id of a product given on customer")
	private Long productId;

	@Schema(name = "product_name", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Tarjeta", description = "Name of a product given on customer")
	private String productName;
}
