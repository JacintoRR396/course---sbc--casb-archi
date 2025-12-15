package com.ssdjr2.pd.customer.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Schema(name = "CustomerRespDTO", description = "DTO than represent a response about one customer")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRespDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = -7040085085805888720L;
	
	@Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1", defaultValue = "0", description = "Unique id of one customer on database")
	private Long id;

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
	
	@Schema(name = "transactions", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Transactions given on customer")
	private List<?> transactions;
}
