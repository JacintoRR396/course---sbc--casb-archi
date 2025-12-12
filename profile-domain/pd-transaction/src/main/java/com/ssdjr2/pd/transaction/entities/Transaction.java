package com.ssdjr2.pd.transaction.entities;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String reference;
	
	private String ibanAccount;
	
	private String channel;
	
	private String status;
	
	private double amount;
	
	private double fee;
	
	private String description;
	
	private LocalDateTime date;
}
