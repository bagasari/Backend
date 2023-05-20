package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    List<Product> findAllByAccountBookId(Long id);
}
