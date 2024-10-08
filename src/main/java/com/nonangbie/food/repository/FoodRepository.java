package com.nonangbie.food.repository;

import com.nonangbie.food.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByFoodName(String foodName);

    Page<Food> findByFoodCategory(Pageable pageable, Food.FoodCategory category);

    Page<Food> findByFoodNameContainingAndFoodCategory(Pageable pageable, String foodName, Food.FoodCategory category);
}
