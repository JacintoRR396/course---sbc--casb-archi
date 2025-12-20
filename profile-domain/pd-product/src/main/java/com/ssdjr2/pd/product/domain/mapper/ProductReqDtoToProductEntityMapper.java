package com.ssdjr2.pd.product.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.core.convert.converter.Converter;

import com.ssdjr2.pd.product.domain.dto.ProductReqDTO;
import com.ssdjr2.pd.product.respository.entity.ProductEntity;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductReqDtoToProductEntityMapper extends Converter<ProductReqDTO, ProductEntity> {

	@Override
	@Mapping(target = "id", ignore = true)
	ProductEntity convert(final ProductReqDTO source);

	void update(final ProductReqDTO dto, @MappingTarget ProductEntity entity);

	List<ProductEntity> convertAll(final List<ProductReqDTO> dtos);
}
