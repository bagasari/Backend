package com.bagasari.sacbagaji.service;

import com.bagasari.sacbagaji.model.entity.Product;
import com.bagasari.sacbagaji.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Slice<Product> searchProduct(String sort, String keyword, Pageable pageable) {
        return productRepository.findAllByNameOrderBySort(sort, keyword, pageable);
    }

}