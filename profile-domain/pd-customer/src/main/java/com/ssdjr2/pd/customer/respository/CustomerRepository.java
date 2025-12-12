package com.ssdjr2.pd.customer.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssdjr2.pd.customer.respository.entity.Customer;

/**
 * @author jacrolrod
 * @version 1.0
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	public Optional<Customer> findByCode(String code);

	public Optional<Customer> findByIban(String iban);
}
