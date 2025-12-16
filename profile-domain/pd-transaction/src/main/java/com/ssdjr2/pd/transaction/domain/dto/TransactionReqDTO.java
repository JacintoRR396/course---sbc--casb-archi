package com.ssdjr2.pd.transaction.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Schema(name = "TransactionReqDTO", description = "DTO than represent a request about one transaction")
@Data
public class TransactionReqDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = -3377622353627270036L;

	@Schema(name = "reference", requiredMode = Schema.RequiredMode.REQUIRED, example = "9524lz", description = "Refence given on transaction")
	private String reference;

	@Schema(name = "ibanAccount", requiredMode = Schema.RequiredMode.REQUIRED, example = "000251491", description = "Iban given on transaction")
	private String ibanAccount;

	@Schema(name = "channel", requiredMode = Schema.RequiredMode.REQUIRED, example = "WEB", defaultValue = "WEB", description = "Channel given on transaction")
	private String channel;

	@Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED, example = "Liquidada", description = "Status given on transaction")
	private String status;
	
	@Schema(name = "amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000.00", description = "Amount given on transaction")
	private Double amount;

	@Schema(name = "fee", requiredMode = Schema.RequiredMode.REQUIRED, example = "10.00", description = "Fee given on transaction")
	private Double fee;

	@Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "This is a example", description = "Description given on transaction")
	private String description;

	@Schema(name = "date", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-08-15T11:36:07", description = "Date given on transaction")
	private LocalDateTime date;
}
