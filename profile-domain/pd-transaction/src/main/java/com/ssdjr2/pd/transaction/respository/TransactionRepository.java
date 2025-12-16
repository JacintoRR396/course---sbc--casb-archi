package com.ssdjr2.pd.transaction.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssdjr2.pd.transaction.entities.TransactionEntity;

/**
 * @author jacrolrod
 * @version 1.0
 */
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

	public List<TransactionEntity> findByIbanAccount(String ibanAccount);
}
