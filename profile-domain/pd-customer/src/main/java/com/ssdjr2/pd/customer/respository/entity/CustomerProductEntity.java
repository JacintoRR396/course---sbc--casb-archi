package com.ssdjr2.pd.customer.respository.entity;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Entity
@Table(name = "customer_product")
@Data
public class CustomerProductEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = -5957201893631805639L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "product_id")
	private Long productId;

	@Transient
	private String productName;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = CustomerEntity.class)
	@JoinColumn(name = "customer_id", nullable = true)
	@JsonIgnore // it is necesary for avoid infinite recursion
	private CustomerEntity customer;
}
