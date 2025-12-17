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

	@Transactional(readOnly = false)
	@Override
	public List<CustomerRespDTO> getAll() {
		return this.customerMapper.fromEntitiesToDtos(this.customerRepo.findAll());
	}

	@Transactional(readOnly = false)
	@Override
	public Optional<CustomerRespDTO> getById(final Long id) {
		return this.customerRepo.findById(id).map(this.customerMapper::fromEntityToDto);
	}

	@Transactional(readOnly = false)
	public Optional<CustomerRespDTO> getByCode(final String code) {
		return this.customerRepo.findByCode(code)
				.map(this.bussinesConsumerService::updateProdsToOneCustomer)
				.map(this.customerMapper::fromEntityToDto);
	}

	@Transactional
	@Override
	public CustomerRespDTO add(final CustomerReqDTO customerReqDTO) throws UnknownHostException, BussinesRuleException {
		CustomerEntity customerEntityReq = this.customerMapper.fromDtoToEntity(customerReqDTO);
		this.bussinesConsumerService.setProdsToOneCustomer(customerEntityReq);

		return this.customerMapper.fromEntityToDto(this.customerRepo.save(customerEntityReq));
	}

	@Transactional
	@Override
	public Optional<CustomerRespDTO> update(final Long id, final CustomerReqDTO customerReqDTO) {
		return this.customerRepo.findById(id)
				.map(customerEntityDB -> this.customerMapper.updateFromDtoToEntity(customerReqDTO, customerEntityDB))
				.map(this.customerRepo::save).map(this.customerMapper::fromEntityToDto);
	}

	@Transactional
	@Override
	public boolean deleteById(Long id) {
		Optional<CustomerEntity> customerEntityDBOpt = this.customerRepo.findById(id);

		customerEntityDBOpt.ifPresent(this.customerRepo::delete);

		return customerEntityDBOpt.isEmpty();
	}
}
