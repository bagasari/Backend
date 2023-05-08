package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.Product;
import com.bagasari.sacbagaji.model.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findOrderByPurchaseDate(Long id) {

        QProduct qp = QProduct.product;

        return queryFactory
                .select(qp)
                .from(qp)
                .where(qp.accountBook.id.eq(id))
                .orderBy(qp.purchaseDate.desc())
                .fetch();

    }

}