package com.ssdjr2.pd.product.respository.entity;

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
@Entity
@Table(name = "product")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String code;

	private String name;
}
