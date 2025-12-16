package com.ssdjr2.pd.product.domain.dto;

import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Schema(name = "ProductReqDTO", description = "DTO than represent a request about one product")
@Data
public class ProductReqDTO implements Serializable {
	
	@Serial
	private static final long serialVersionUID = -2181660463178274596L;

	@Schema(name = "code", requiredMode = Schema.RequiredMode.REQUIRED, example = "000003", defaultValue = "000001", description = "Code given on product")
	private String code;

	@Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Tarjeta", description = "Name given on product")
	private String name;
}
