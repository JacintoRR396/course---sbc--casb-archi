package com.ssdjr2.pd.transaction.service;

import java.util.List;
import java.util.Optional;

import com.ssdjr2.pd.transaction.domain.dto.TransactionReqDTO;
import com.ssdjr2.pd.transaction.domain.dto.TransactionRespDTO;

/**
 * @author jacrolrod
 * @version 1.0
 */
public interface TransactionService {

	List<TransactionRespDTO> getAll();

	List<TransactionRespDTO> getAllByIban(final String iban);

	Optional<TransactionRespDTO> getById(final Long id);

	TransactionRespDTO add(final TransactionReqDTO transactionReqDTO);

	Optional<TransactionRespDTO> udpate(final Long id, final TransactionReqDTO transactionReqDTO);

	boolean deleteById(final Long id);
}
