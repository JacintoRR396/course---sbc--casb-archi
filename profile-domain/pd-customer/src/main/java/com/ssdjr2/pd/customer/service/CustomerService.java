package com.ssdjr2.pd.customer.service;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import com.ssdjr2.pd.customer.domain.dto.CustomerReqDTO;
import com.ssdjr2.pd.customer.domain.dto.CustomerRespDTO;
import com.ssdjr2.pd.customer.exception.BussinesRuleException;

/**
 * @author jacrolrod
 * @version 1.0
 */
public interface CustomerService {

	List<CustomerRespDTO> getAll();

	Optional<CustomerRespDTO> getById(final Long id);

	Optional<CustomerRespDTO> getByCode(final String code);

	CustomerRespDTO add(final CustomerReqDTO customerReqDTO) throws UnknownHostException, BussinesRuleException;

	Optional<CustomerRespDTO> update(final Long id, final CustomerReqDTO customerReqDTO);

	boolean deleteById(final Long id);
}
