package com.ssdjr2.pd.product.service;

import java.util.List;
import java.util.Optional;

import com.ssdjr2.pd.product.domain.dto.ProductReqDTO;
import com.ssdjr2.pd.product.domain.dto.ProductRespDTO;

/**
 * @author jacrolrod
 * @version 1.0
 */
public interface ProductService {

	List<ProductRespDTO> getAll();

	Optional<ProductRespDTO> getById(final Long id);

	ProductRespDTO add(final ProductReqDTO productReqDTO);

	Optional<ProductRespDTO> update(final Long id, final ProductReqDTO productReqDTO);

	boolean deleteById(final Long id);
}
