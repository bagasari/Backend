package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductRepositoryCustom {

    Slice<ProductDTO> findAllByNameAndLocationOrderBySort(String keyword, String location, Pageable pageable, Long lastId);

    List<String> findNameListOrderByProductNameAsc(String word);
}
