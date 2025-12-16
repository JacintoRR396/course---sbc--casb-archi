package com.ssdjr2.pd.transaction.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssdjr2.pd.transaction.domain.dto.TransactionReqDTO;
import com.ssdjr2.pd.transaction.domain.dto.TransactionRespDTO;
import com.ssdjr2.pd.transaction.domain.mapper.TransactionMapper;
import com.ssdjr2.pd.transaction.entities.TransactionEntity;
import com.ssdjr2.pd.transaction.respository.TransactionRepository;
import com.ssdjr2.pd.transaction.service.TransactionService;

import lombok.AllArgsConstructor;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionMapper transactionMapper;

	private final TransactionRepository transactionRepo;

	@Override
	public List<TransactionRespDTO> getAll() {
		return this.transactionMapper.toDtos(this.transactionRepo.findAll());
	}

	@Override
	public List<TransactionRespDTO> getAllByIban(final String iban) {
		return this.transactionMapper.toDtos(this.transactionRepo.findByIbanAccount(iban));
	}

	@Override
	public Optional<TransactionRespDTO> getById(final Long id) {
		return this.transactionRepo.findById(id).map(this.transactionMapper::toDto);
	}

	@Transactional
	@Override
	public TransactionRespDTO add(final TransactionReqDTO transactionReqDTO) {
		return this.transactionMapper
				.toDto(this.transactionRepo.save(this.transactionMapper.toEntity(transactionReqDTO)));
	}

	@Transactional
	@Override
	public Optional<TransactionRespDTO> udpate(final Long id, final TransactionReqDTO transactionReqDTO) {
		return this.transactionRepo.findById(id).map(transactionEntityDB -> {
			this.applyUpdates(transactionReqDTO, transactionEntityDB);
			TransactionEntity updatedTransactionEntityDB = this.transactionRepo.save(transactionEntityDB);

			return this.transactionMapper.toDto(updatedTransactionEntityDB);
		});
	}

	private void applyUpdates(final TransactionReqDTO transactionReqDTO, final TransactionEntity transactionEntityDB) {
		transactionEntityDB.setReference(transactionReqDTO.getReference());
		transactionEntityDB.setIbanAccount(transactionReqDTO.getIbanAccount());
		transactionEntityDB.setChannel(transactionReqDTO.getChannel());
		transactionEntityDB.setStatus(transactionReqDTO.getStatus());
		transactionEntityDB.setAmount(transactionReqDTO.getAmount());
		transactionEntityDB.setFee(transactionReqDTO.getFee());
		transactionEntityDB.setDescription(transactionReqDTO.getDescription());
		transactionEntityDB.setDate(transactionReqDTO.getDate());
	}

	@Transactional
	@Override
	public boolean deleteById(Long id) {
		Optional<TransactionEntity> transactionEntityDBOpt = this.transactionRepo.findById(id);

		transactionEntityDBOpt.ifPresent(this.transactionRepo::delete);

		return transactionEntityDBOpt.isPresent();
	}
}
