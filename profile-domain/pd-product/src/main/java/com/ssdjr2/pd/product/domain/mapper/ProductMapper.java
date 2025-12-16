package com.ssdjr2.pd.product.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ssdjr2.pd.product.domain.dto.ProductReqDTO;
import com.ssdjr2.pd.product.domain.dto.ProductRespDTO;
import com.ssdjr2.pd.product.respository.entity.ProductEntity;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

	@Mapping(target = "id", ignore = true)
	ProductEntity toEntity(ProductReqDTO dto);

	List<ProductEntity> toEntities(List<ProductReqDTO> dtos);

	ProductRespDTO toDto(ProductEntity entity);

	List<ProductRespDTO> toDtos(List<ProductEntity> entities);
}
