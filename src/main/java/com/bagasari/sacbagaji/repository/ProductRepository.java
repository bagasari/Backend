package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
