package com.ssdjr2.pd.product.respository.entity;

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
@Entity
@Table(name = "product")
@Data
public class Product {

	@Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "product_generator"
    )
    @SequenceGenerator(
        name = "product_generator",
        sequenceName = "PRODUCT_SEQ",
        allocationSize = 1
    )
	private Long id;

	private String code;

	private String name;
}
