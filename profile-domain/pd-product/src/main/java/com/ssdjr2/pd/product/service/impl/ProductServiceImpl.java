package com.ssdjr2.pd.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssdjr2.pd.product.domain.dto.ProductReqDTO;
import com.ssdjr2.pd.product.domain.dto.ProductRespDTO;
import com.ssdjr2.pd.product.domain.mapper.ProductMapper;
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

	private final ProductMapper productMapper;

	private final ProductRepository productRepo;

	@Transactional(readOnly = false)
	@Override
	public List<ProductRespDTO> getAll() {
		return this.productMapper.toDtos(this.productRepo.findAll());
	}

	@Transactional(readOnly = false)
	@Override
	public Optional<ProductRespDTO> getById(final Long id) {
		return this.productRepo.findById(id).map(this.productMapper::toDto);
	}

	@Transactional
	@Override
	public ProductRespDTO add(final ProductReqDTO productReqDTO) {
		return this.productMapper.toDto(this.productRepo.save(this.productMapper.toEntity(productReqDTO)));
	}

	@Transactional
	@Override
	public Optional<ProductRespDTO> update(final Long id, final ProductReqDTO productReqDTO) {
		return this.productRepo.findById(id).map(productEntityDB -> {
			this.applyUpdates(productReqDTO, productEntityDB);
			ProductEntity updatedProductEntityDB = this.productRepo.save(productEntityDB);

			return this.productMapper.toDto(updatedProductEntityDB);
		});
	}

	private void applyUpdates(final ProductReqDTO productReqDTO, final ProductEntity productEntityDB) {
		productEntityDB.setCode(productReqDTO.getCode());
		productEntityDB.setName(productReqDTO.getName());
	}

	@Transactional
	@Override
	public boolean deleteById(final Long id) {
		Optional<ProductEntity> productEntityDBOpt = this.productRepo.findById(id);

		productEntityDBOpt.ifPresent(this.productRepo::delete);

		return productEntityDBOpt.isPresent();
	}
}
