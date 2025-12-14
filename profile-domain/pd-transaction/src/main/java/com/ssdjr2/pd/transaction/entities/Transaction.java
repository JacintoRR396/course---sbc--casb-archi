package com.ssdjr2.pd.transaction.entities;

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
public class Transaction {

	@Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "transaction_generator"
    )
    @SequenceGenerator(
        name = "transaction_generator",
        sequenceName = "TRANSACTION_SEQ",
        allocationSize = 1
    )
	private Long id;
	
	private String reference;
	
	@Column(name = "iban_account")
	private String ibanAccount;
	
	private String channel;
	
	private String status;
	
	private double amount;
	
	private double fee;
	
	private String description;
	
	private LocalDateTime date;
}
