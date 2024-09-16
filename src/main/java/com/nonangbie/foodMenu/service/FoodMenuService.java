package com.nonangbie.foodMenu.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.food.entity.Food;
import com.nonangbie.food.service.FoodService;
import com.nonangbie.foodMenu.entity.FoodMenu;
import com.nonangbie.foodMenu.repository.FoodMenuRepository;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.utils.ExtractMemberEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class FoodMenuService extends ExtractMemberEmail {
    private final FoodMenuRepository foodMenuRepository;
    private final FoodService foodService;
    private final MemberRepository memberRepository;

    public FoodMenu createFoodMenu(FoodMenu foodMenu, Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
        Food food = foodService.findVerifiedFood(foodMenu.getFood().getFoodId(),authentication);
        foodMenu.setFood(food);
        FoodMenu saveFoodMenu = foodMenuRepository.save(foodMenu);
        return saveFoodMenu;
    }

    public FoodMenu updateFoodMenu(FoodMenu foodMenu, Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
        FoodMenu findFoodMenu = findVerifiedFoodMenu(foodMenu.getFoodMenuId(),authentication);
        Optional.ofNullable(foodMenu.getFoodQuantity())
                .ifPresent(quantity -> findFoodMenu.setFoodQuantity(quantity));
        return foodMenuRepository.save(findFoodMenu);
    }

    public Page<FoodMenu> findFoodMenus(int page, int size,Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
        return foodMenuRepository.findAll(PageRequest.of(page, size,
                Sort.by("foodMenuId").descending()));
    }

    public void deleteFoodMenu(long foodMenuId,Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
        FoodMenu findFoodMenu = findVerifiedFoodMenu(foodMenuId,authentication);
        foodMenuRepository.delete(findFoodMenu);

    }


    public FoodMenu findVerifiedFoodMenu(long foodMenuId,Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
        Optional<FoodMenu> optionalFoodMenu = foodMenuRepository.findById(foodMenuId);
        FoodMenu findFoodMenu = optionalFoodMenu.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOOD_MENU_NOT_FOUND));
        return findFoodMenu;
    }
}
