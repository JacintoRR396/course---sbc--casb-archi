package com.ssdjr2.pd.customer.domain.dto;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Schema(name = "CustomerProductRespDTO", description = "DTO than represent a response about one product of a customer")
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerProductRespDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = -6073040784183885522L;

	@Schema(name = "product_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1", defaultValue = "0", description = "Unique id of a product given on customer")
	private Long productId;

	@Schema(name = "product_name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Tarjeta", description = "Name of a product given on customer")
	private String productName;
}
