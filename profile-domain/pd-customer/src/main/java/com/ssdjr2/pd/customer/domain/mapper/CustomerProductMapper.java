package com.ssdjr2.pd.customer.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ssdjr2.pd.customer.domain.dto.CustomerProductReqDTO;
import com.ssdjr2.pd.customer.domain.dto.CustomerProductRespDTO;
import com.ssdjr2.pd.customer.respository.entity.CustomerProductEntity;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerProductMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "customer", ignore = true)
	CustomerProductEntity toEntity(CustomerProductReqDTO dto);

	List<CustomerProductEntity> toEntities(List<CustomerProductReqDTO> dtos);

	CustomerProductRespDTO toDto(CustomerProductEntity entity);

	List<CustomerProductRespDTO> toDtos(List<CustomerProductEntity> entity);
}
