package com.nonangbie.food.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.food.entity.Food;
import com.nonangbie.food.repository.FoodRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Food createFood(Food food) {
        Food saveFood = foodRepository.save(food);
        return saveFood;
    }

    public Food updateFood(Food food) {
        Food findFood = findVerifiedFood(food.getFoodId());

        Optional.ofNullable(food.getFoodName())
                .ifPresent(foodName -> findFood.setFoodName(foodName));
        Optional.ofNullable(food.getFoodCategory())
                .ifPresent(foodCategory -> findFood.setFoodCategory(foodCategory));

        return foodRepository.save(findFood);
    }

    public Food findFood(long foodId) {
        Food findFood = findVerifiedFood(foodId);
        return findFood;
    }

    public Page<Food> findFoodsSort(int page, int size, Sort sort, Food.FoodCategory category) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return foodRepository.findByFoodCategory(pageable, category);
    }

    public Page<Food> search(Pageable pageable, String keyword, Food.FoodCategory category) {
        return foodRepository.findByFoodNameContainingAndFoodCategory(pageable, keyword, category);
    }

    public void deleteFood(long foodId) {
        Food findFood = findVerifiedFood(foodId);
        foodRepository.delete(findFood);
    }

    public Food findVerifiedFood(long foodId) {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        Food findFood = optionalFood.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.FOOD_NOT_FOUND));
        return findFood;
    }
}
