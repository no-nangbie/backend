package com.nonangbie.foodMenu.repository;

import com.nonangbie.foodMenu.entity.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
}
