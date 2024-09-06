package com.nonangbie.memberFood.repository;

import com.nonangbie.memberFood.entity.MemberFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberFoodRepository extends JpaRepository<MemberFood, Long> {

    List<MemberFood> findByFood_FoodNameContainingOrMemoContaining(String foodNameKeyword, String memoKeyword);
}
