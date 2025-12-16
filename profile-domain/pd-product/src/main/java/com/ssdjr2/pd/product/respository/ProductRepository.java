package com.ssdjr2.pd.product.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssdjr2.pd.product.respository.entity.ProductEntity;

/**
 * @author jacrolrod
 * @version 1.0
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
