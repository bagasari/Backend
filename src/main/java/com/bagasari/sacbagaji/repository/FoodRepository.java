package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByName(String name);
}
