package com.ssdjr2.pd.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssdjr2.pd.product.domain.dto.ProductReqDTO;
import com.ssdjr2.pd.product.domain.dto.ProductRespDTO;
import com.ssdjr2.pd.product.domain.mapper.ProductEntityToProductRespDtoMapper;
import com.ssdjr2.pd.product.domain.mapper.ProductReqDtoToProductEntityMapper;
import com.ssdjr2.pd.product.respository.ProductRepository;
import com.ssdjr2.pd.product.respository.entity.ProductEntity;
import com.ssdjr2.pd.product.service.ProductService;

import lombok.AllArgsConstructor;

/**
 * @author jacrolrod
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductReqDtoToProductEntityMapper prodReqDtoToEntityMapper;

	private final ProductEntityToProductRespDtoMapper prodEntityToRespDtoMapper;

	private final ProductRepository productRepo;

	@Transactional(readOnly = false)
	@Override
	public List<ProductRespDTO> getAll() {
		List<ProductEntity> prodEntitiesDB = this.productRepo.findAll();

		return this.prodEntityToRespDtoMapper.convertAll(prodEntitiesDB);
	}

	@Transactional(readOnly = false)
	@Override
	public Optional<ProductRespDTO> getById(final Long id) {
		return this.productRepo.findById(id).map(this.prodEntityToRespDtoMapper::convert);
	}

	@Transactional
	@Override
	public ProductRespDTO add(final ProductReqDTO productReqDTO) {
		ProductEntity prodEntityReq = this.prodReqDtoToEntityMapper.convert(productReqDTO);

		ProductEntity prodEntityAddDB = this.productRepo.save(prodEntityReq);

		return this.prodEntityToRespDtoMapper.convert(prodEntityAddDB);
	}

	@Transactional
	@Override
	public Optional<ProductRespDTO> update(final Long id, final ProductReqDTO productReqDTO) {
		return productRepo.findById(id).map(productEntityDB -> {
			this.prodReqDtoToEntityMapper.update(productReqDTO, productEntityDB);

			ProductEntity productEntityUpdatedDB = this.productRepo.save(productEntityDB);

			return this.prodEntityToRespDtoMapper.convert(productEntityUpdatedDB);
		});
	}

	@Transactional
	@Override
	public boolean deleteById(final Long id) {
		Optional<ProductEntity> productEntityDBOpt = this.productRepo.findById(id);

		productEntityDBOpt.ifPresent(this.productRepo::delete);

		return productEntityDBOpt.isPresent();
	}
}
