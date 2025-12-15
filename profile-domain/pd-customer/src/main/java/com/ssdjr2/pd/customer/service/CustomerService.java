package com.ssdjr2.pd.customer.service;

import java.net.UnknownHostException;
import java.util.List;

import com.ssdjr2.pd.customer.domain.dto.CustomerReqDTO;
import com.ssdjr2.pd.customer.domain.dto.CustomerRespDTO;
import com.ssdjr2.pd.customer.exception.BussinesRuleException;

/**
 * @author jacrolrod
 * @version 1.0
 */
public interface CustomerService {

	List<CustomerRespDTO> getAll();
	
	CustomerRespDTO getById(final Long id);
	
	CustomerRespDTO getByCode(final String code);
	
	CustomerRespDTO add(final CustomerReqDTO customerReqDTO) throws UnknownHostException, BussinesRuleException;
	
	CustomerRespDTO udpate(final Long id, final CustomerReqDTO customerReqDTO);
	
	boolean deleteById(final Long id);
}
