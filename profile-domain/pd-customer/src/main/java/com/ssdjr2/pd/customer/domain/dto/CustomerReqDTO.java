package com.ssdjr2.pd.customer.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Schema(name = "CustomerReqDTO", description = "DTO than represent a request about one customer")
@Data
public class CustomerReqDTO implements Serializable {
	
	@Serial
	private static final long serialVersionUID = -2014534545180715915L;

	@Schema(name = "code", requiredMode = Schema.RequiredMode.REQUIRED, example = "000003", defaultValue = "000001", description = "Code given on customer")
	private String code;

	@Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Juan", description = "Name given on customer")
	private String name;

	@Schema(name = "surname", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Rodriguez Roldan", description = "Surname given on customer")
	private String surname;

	@Schema(name = "phone", requiredMode = Schema.RequiredMode.REQUIRED, example = "666999333", description = "Personal phone given on customer")
	private String phone;

	@Schema(name = "address", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Jesus Nazareno 22", description = "Personal address given on customer")
	private String address;

	@Schema(name = "iban", requiredMode = Schema.RequiredMode.REQUIRED, example = "000251491", description = "Iban account given on customer")
	private String iban;

	@Schema(name = "products", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Products given on customer")
	private List<CustomerProductReqDTO> products;
}
