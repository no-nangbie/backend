package com.nonangbie.memberFood.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.food.entity.Food;
import com.nonangbie.food.repository.FoodRepository;
import com.nonangbie.food.service.FoodService;
import com.nonangbie.member.entity.Member;
import com.nonangbie.memberFood.entity.MemberFood;
import com.nonangbie.memberFood.repository.MemberFoodRepository;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberFoodService {
    private final MemberFoodRepository memberFoodRepository;
    private final FoodRepository foodRepository;

    public MemberFoodService(MemberFoodRepository memberFoodRepository,
                             FoodRepository foodRepository) {
        this.memberFoodRepository = memberFoodRepository;
        this.foodRepository = foodRepository;
    }

    public MemberFood createMemberFood(MemberFood memberFood) {
        Food findFood = foodRepository.findByFoodName(memberFood.getFood().getFoodName())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOOD_NOT_FOUND));
        memberFood.setFood(findFood);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate expirationDate = LocalDate.parse(memberFood.getExpirationDate(), formatter);
        LocalDate currentDate = LocalDate.now();
        long remainingShelfLife = ChronoUnit.DAYS.between(currentDate, expirationDate);

        if (remainingShelfLife >= 0 && remainingShelfLife <= 3) {
            memberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Approaching_Expiry);
        } else if (remainingShelfLife < 0) {
            memberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Near_Expiry);
        } else {
            memberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Fresh);
        }

        return memberFoodRepository.save(memberFood);
    }

    public MemberFood updateMemberFood(MemberFood memberFood) {
        MemberFood findMemberFood = findVerifiedMemberFood(memberFood.getMemberFoodId());

        Optional.ofNullable(memberFood.getFood())
                .map(Food::getFoodName)
                .ifPresent(foodName -> {
                    Food findFood = foodRepository.findByFoodName(foodName)
                            .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOOD_NOT_FOUND));
                    findMemberFood.setFood(findFood);
                });
        Optional.ofNullable(memberFood.getFoodCategory())
                .ifPresent(foodCategory -> findMemberFood.setFoodCategory(foodCategory));
        Optional.ofNullable(memberFood.getExpirationDate())
                .ifPresent(expirationDate -> findMemberFood.setExpirationDate(expirationDate));
        Optional.ofNullable(memberFood.getMemo())
                .ifPresent(memo -> findMemberFood.setMemo(memo));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate expirationDate = LocalDate.parse(memberFood.getExpirationDate(), formatter);
        LocalDate currentDate = LocalDate.now();
        long remainingShelfLife = ChronoUnit.DAYS.between(currentDate, expirationDate);

        if (remainingShelfLife >= 0 && remainingShelfLife <= 3) {
            memberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Approaching_Expiry);
        } else if (remainingShelfLife < 0) {
            memberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Near_Expiry);
        } else {
            memberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Fresh);
        }

        return memberFoodRepository.save(findMemberFood);
    }

    public MemberFood findMemberFood(long memberFoodId) {
        return findVerifiedMemberFood(memberFoodId);
    }

    public Page<MemberFood> findMemberFoodsSort(int page, int size, Sort sort) {
        return memberFoodRepository.findAll(PageRequest.of(page, size, sort));
    }

    public List<MemberFood> searchMemberFoodsByKeyword(String keyword) {
        return memberFoodRepository.findByFood_FoodNameContainingOrMemoContaining(keyword, keyword);
    }

    // 카테고리 내에서 검색
    public Page<MemberFood> searchByCategory(Pageable pageable, String keyword, Food.FoodCategory foodCategory) {
        Page<Food> foods = foodRepository.findByFoodNameContainingAndFoodCategory(pageable, keyword, foodCategory);

        List<MemberFood> memberFoods = foods.stream()
                .flatMap(food -> memberFoodRepository.findByFood_FoodId(food.getFoodId()).stream())
                .collect(Collectors.toList());


        return new PageImpl<>(memberFoods, pageable, memberFoods.size());
    }

    public void deleteMemberFood(long memberFoodId) {
        MemberFood findMemberFood = findMemberFood(memberFoodId);
        memberFoodRepository.delete(findMemberFood);
    }

    public MemberFood findVerifiedMemberFood(long memberFoodId) {
        Optional<MemberFood> optionalMemberFood = memberFoodRepository.findById(memberFoodId);
        MemberFood findmemberFood = optionalMemberFood.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_FOOD_NOT_FOUND));
        return findmemberFood;
    }
}
