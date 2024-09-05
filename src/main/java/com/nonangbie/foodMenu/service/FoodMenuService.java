package com.nonangbie.foodMenu.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.food.entity.Food;
import com.nonangbie.food.service.FoodService;
import com.nonangbie.foodMenu.entity.FoodMenu;
import com.nonangbie.foodMenu.repository.FoodMenuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class FoodMenuService {
    private final FoodMenuRepository foodMenuRepository;
    private final FoodService foodService;

    public FoodMenuService(FoodMenuRepository foodMenuRepository,
                           FoodService foodService) {
        this.foodMenuRepository = foodMenuRepository;
        this.foodService = foodService;
    }

    public FoodMenu createFoodMenu(FoodMenu foodMenu) {
        Food food = foodService.findVerifiedFood(foodMenu.getFood().getFoodId());
        foodMenu.setFood(food);
        FoodMenu saveFoodMenu = foodMenuRepository.save(foodMenu);
        return saveFoodMenu;
    }

    public FoodMenu updateFoodMenu(FoodMenu foodMenu) {
        FoodMenu findFoodMenu = findVerifiedFoodMenu(foodMenu.getFoodMenuId());
        Optional.ofNullable(foodMenu.getFoodQuantity())
                .ifPresent(quantity -> findFoodMenu.setFoodQuantity(quantity));
        return foodMenuRepository.save(findFoodMenu);
    }

    public Page<FoodMenu> findFoodMenus(int page, int size) {
        return foodMenuRepository.findAll(PageRequest.of(page, size,
                Sort.by("foodMenuId").descending()));
    }

    public void deleteFoodMenu(long foodMenuId) {
        FoodMenu findFoodMenu = findVerifiedFoodMenu(foodMenuId);
        foodMenuRepository.delete(findFoodMenu);

    }


    public FoodMenu findVerifiedFoodMenu(long foodMenuId) {
        Optional<FoodMenu> optionalFoodMenu = foodMenuRepository.findById(foodMenuId);
        FoodMenu findFoodMenu = optionalFoodMenu.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOOD_MENU_NOT_FOUND));
        return findFoodMenu;
    }
}
