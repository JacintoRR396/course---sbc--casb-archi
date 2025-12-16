package com.ssdjr2.pd.transaction.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ssdjr2.pd.transaction.domain.dto.TransactionReqDTO;
import com.ssdjr2.pd.transaction.domain.dto.TransactionRespDTO;
import com.ssdjr2.pd.transaction.entities.TransactionEntity;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

	@Mapping(target = "id", ignore = true)
	TransactionEntity toEntity(TransactionReqDTO dto);

	List<TransactionEntity> toEntities(List<TransactionReqDTO> dtos);

	TransactionRespDTO toDto(TransactionEntity entity);

	List<TransactionRespDTO> toDtos(List<TransactionEntity> entities);
}
