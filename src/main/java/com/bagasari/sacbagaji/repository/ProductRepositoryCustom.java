package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findOrderByPurchaseDate(Long id);
}
