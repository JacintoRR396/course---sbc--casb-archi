package com.ssdjr2.pd.product.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.core.convert.converter.Converter;

import com.ssdjr2.pd.product.domain.dto.ProductRespDTO;
import com.ssdjr2.pd.product.respository.entity.ProductEntity;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductEntityToProductRespDtoMapper extends Converter<ProductEntity, ProductRespDTO> {

	@Override
	ProductRespDTO convert(final ProductEntity entity);

	List<ProductRespDTO> convertAll(final List<ProductEntity> entities);
}
