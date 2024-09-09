package com.nonangbie.food.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.food.entity.Food;
import com.nonangbie.food.repository.FoodRepository;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.utils.ExtractMemberEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class FoodService extends ExtractMemberEmail {
    private final FoodRepository foodRepository;
    private final MemberRepository memberRepository;

    public Food createFood(Food food, Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        return foodRepository.save(food);
    }

    public Food updateFood(Food food, Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Food findFood = foodRepository.findById(food.getFoodId())
                        .orElseThrow(()-> new BusinessLogicException(ExceptionCode.FOOD_NOT_FOUND));

        Optional.ofNullable(food.getFoodName())
                .ifPresent(foodName -> findFood.setFoodName(foodName));
        Optional.ofNullable(food.getFoodCategory())
                .ifPresent(foodCategory -> findFood.setFoodCategory(foodCategory));

        return foodRepository.save(findFood);
    }

    public Food findFood(Food food,Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Food findFood = foodRepository.findById(food.getFoodId())
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.FOOD_NOT_FOUND));
        return findFood;
    }

    public Page<Food> findFoodsSort(int page, int size, Sort sort, Food.FoodCategory category,Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Pageable pageable = PageRequest.of(page, size, sort);
        return foodRepository.findByFoodCategory(pageable, category);
    }

    public Page<Food> search(Pageable pageable, String keyword, Food.FoodCategory category,Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        return foodRepository.findByFoodNameContainingAndFoodCategory(pageable, keyword, category);
    }

    public void deleteFood(long foodId, Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Food findFood = findVerifiedFood(foodId,authentication);
        foodRepository.delete(findFood);
    }

    @Transactional(readOnly = true)
    public Food findVerifiedFood(long foodId,Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        Food findFood = optionalFood.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.FOOD_NOT_FOUND));
        return findFood;
    }
}
