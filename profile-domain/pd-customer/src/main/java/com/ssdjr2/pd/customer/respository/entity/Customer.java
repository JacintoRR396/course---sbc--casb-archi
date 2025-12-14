package com.ssdjr2.pd.customer.respository.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Entity
@Table(name = "customer")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String code;

	private String name;

	private String surname;

	private String phone;

	private String address;

	private String iban;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomerProduct> products;

	@Transient
	private List<?> transactions;
}
