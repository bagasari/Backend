package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.Product;
import com.bagasari.sacbagaji.model.entity.ProductLike;
import com.bagasari.sacbagaji.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
    void deleteByUserAndProduct(User user, Product product);

    boolean existsByUserAndProduct(User user, Product product);
}
