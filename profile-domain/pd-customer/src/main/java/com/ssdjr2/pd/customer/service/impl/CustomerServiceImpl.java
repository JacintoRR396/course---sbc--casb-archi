package com.ssdjr2.pd.customer.service.impl;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssdjr2.pd.customer.domain.dto.CustomerReqDTO;
import com.ssdjr2.pd.customer.domain.dto.CustomerRespDTO;
import com.ssdjr2.pd.customer.domain.mapper.CustomerMapper;
import com.ssdjr2.pd.customer.exception.BussinesRuleException;
import com.ssdjr2.pd.customer.respository.CustomerRepository;
import com.ssdjr2.pd.customer.respository.entity.CustomerEntity;
import com.ssdjr2.pd.customer.service.BussinesConsumerService;
import com.ssdjr2.pd.customer.service.CustomerService;

import lombok.AllArgsConstructor;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerMapper customerMapper;

	private final CustomerRepository customerRepo;

	private final BussinesConsumerService bussinesConsumerService;

	@Override
	public List<CustomerRespDTO> getAll() {
		List<CustomerEntity> customerEntitiesDB = this.customerRepo.findAll();

		return this.customerMapper.toDtos(customerEntitiesDB);
	}

	@Override
	public CustomerRespDTO getById(final Long id) {
		Optional<CustomerEntity> customerEntityDBOpt = this.customerRepo.findById(id);
		if (customerEntityDBOpt.isPresent()) {
			CustomerEntity customerEntityDB = customerEntityDBOpt.get();

			return this.customerMapper.toDto(customerEntityDB);
		} else {
			return null;
		}
	}

	@Override
	public CustomerRespDTO getByCode(final String code) {
		Optional<CustomerEntity> customerEntityDBOpt = this.customerRepo.findByCode(code);
		if (customerEntityDBOpt.isPresent()) {
			CustomerEntity customerEntityDB = customerEntityDBOpt.get();
			this.bussinesConsumerService.updateProdsToOneCustomer(customerEntityDB);

			return this.customerMapper.toDto(customerEntityDB);
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public CustomerRespDTO add(final CustomerReqDTO customerReqDTO) throws UnknownHostException, BussinesRuleException {
		CustomerEntity customerEntityReq = this.customerMapper.toEntity(customerReqDTO);
		this.bussinesConsumerService.setProdsToOneCustomer(customerEntityReq);

		CustomerEntity customerEntityDB = this.customerRepo.save(customerEntityReq);

		return this.customerMapper.toDto(customerEntityDB);
	}

	@Transactional
	@Override
	public CustomerRespDTO udpate(final Long id, final CustomerReqDTO customerReqDTO) {
		Optional<CustomerEntity> customerEntityDBOpt = this.customerRepo.findById(id);
		if (customerEntityDBOpt.isPresent()) {
			CustomerEntity customerEntityDB = customerEntityDBOpt.get();
			customerEntityDB.setCode(customerReqDTO.getCode());
			customerEntityDB.setName(customerReqDTO.getName());
			customerEntityDB.setSurname(customerReqDTO.getSurname());
			customerEntityDB.setPhone(customerReqDTO.getPhone());
			customerEntityDB.setAddress(customerReqDTO.getAddress());
			customerEntityDB.setIban(customerReqDTO.getIban());

			customerEntityDB = this.customerRepo.save(customerEntityDB);

			return this.customerMapper.toDto(customerEntityDB);
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public boolean deleteById(Long id) {
		Optional<CustomerEntity> customerEntityDBOpt = this.customerRepo.findById(id);
		if (customerEntityDBOpt.isPresent()) {
			CustomerEntity customerEntityDB = customerEntityDBOpt.get();

			this.customerRepo.delete(customerEntityDB);

			return true;
		}

		return false;
	}
}
