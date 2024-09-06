package com.nonangbie.memberFood.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.food.entity.Food;
import com.nonangbie.food.service.FoodService;
import com.nonangbie.member.entity.Member;
import com.nonangbie.memberFood.entity.MemberFood;
import com.nonangbie.memberFood.repository.MemberFoodRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberFoodService {
    private final MemberFoodRepository memberFoodRepository;
    private final FoodService foodService;

    public MemberFoodService(MemberFoodRepository memberFoodRepository,
                             FoodService foodService) {
        this.memberFoodRepository = memberFoodRepository;
        this.foodService = foodService;
    }

    public MemberFood createMemberFood(MemberFood memberFood) {
        Food findFood = foodService.findVerifiedFood(memberFood.getFood().getFoodId());
        memberFood.setFood(findFood);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate expirationDate = LocalDate.parse(memberFood.getExpirationDate(), formatter);
        LocalDate currentDate = LocalDate.now();
        long remainingShelfLife = ChronoUnit.DAYS.between(currentDate, expirationDate);

        if (remainingShelfLife > 0 && remainingShelfLife <= 3) {
            memberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Approaching_Expiry);
        } else if (remainingShelfLife <= 0) {
            memberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Near_Expiry);
        } else {
            memberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Fresh);
        }

        return memberFoodRepository.save(memberFood);
    }

    public MemberFood updateMemberFood(MemberFood memberFood) {
        MemberFood findMemberFood = findVerifiedMemberFood(memberFood.getMemberFoodId());

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

        if (remainingShelfLife > 0 && remainingShelfLife <= 3) {
            memberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Approaching_Expiry);
        } else if (remainingShelfLife <= 0) {
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
