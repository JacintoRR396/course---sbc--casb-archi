package com.ssdjr2.pd.customer.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssdjr2.pd.customer.respository.entity.CustomerEntity;

/**
 * @author jacrolrod
 * @version 1.0
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

	public Optional<CustomerEntity> findByCode(String code);

	public Optional<CustomerEntity> findByIban(String iban);
}
