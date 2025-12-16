package com.ssdjr2.pd.transaction.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Data
@Table(name = "transaction")
@Entity
public class TransactionEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = -1807144526747014799L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_generator")
	@SequenceGenerator(name = "transaction_generator", sequenceName = "TRANSACTION_SEQ", allocationSize = 1)
	private Long id;

	private String reference;

	@Column(name = "iban_account")
	private String ibanAccount;

	private String channel;

	private String status;

	private Double amount;

	private Double fee;

	private String description;

	private LocalDateTime date;
}
