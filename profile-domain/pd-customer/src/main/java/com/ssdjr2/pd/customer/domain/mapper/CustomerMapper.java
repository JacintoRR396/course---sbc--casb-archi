package com.ssdjr2.pd.customer.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.ssdjr2.pd.customer.domain.dto.CustomerReqDTO;
import com.ssdjr2.pd.customer.domain.dto.CustomerRespDTO;
import com.ssdjr2.pd.customer.respository.entity.CustomerEntity;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "transactions", ignore = true)
	CustomerEntity fromDtoToEntity(CustomerReqDTO dto);
	
	CustomerEntity updateFromDtoToEntity(CustomerReqDTO dto, @MappingTarget CustomerEntity entity);

	List<CustomerEntity> fromDtosToEntities(List<CustomerReqDTO> dtos);

	CustomerRespDTO fromEntityToDto(CustomerEntity entity);

	List<CustomerRespDTO> fromEntitiesToDtos(List<CustomerEntity> entities);
}
