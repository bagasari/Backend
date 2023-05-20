package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import com.bagasari.sacbagaji.model.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {

    Slice<ProductDTO> findAllByNameOrderBySort(String keyword, Pageable pageable, Long lastId);
}
