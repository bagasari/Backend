package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Slice<Product> findAllByNameOrderBySort(String sort, String keyword, Pageable pageable) {
        //TODO
    }
}
