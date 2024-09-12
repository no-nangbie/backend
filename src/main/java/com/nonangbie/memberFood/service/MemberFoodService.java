package com.nonangbie.memberFood.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.food.entity.Food;
import com.nonangbie.food.repository.FoodRepository;
import com.nonangbie.food.service.FoodService;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.memberFood.entity.MemberFood;
import com.nonangbie.memberFood.repository.MemberFoodRepository;
import com.nonangbie.utils.ExtractMemberEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberFoodService extends ExtractMemberEmail {
    private final MemberFoodRepository memberFoodRepository;
    private final FoodRepository foodRepository;
    private final MemberRepository memberRepository;

    public MemberFood createMemberFood(MemberFood memberFood, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication,memberRepository);
        Food findFood = foodRepository.findByFoodName(memberFood.getFood().getFoodName())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOOD_NOT_FOUND));
        memberFood.setFood(findFood);
        memberFood.setMember(member);
        memberFood.setFoodName(findFood.getFoodName());

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

    public MemberFood updateMemberFood(MemberFood memberFood,Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication,memberRepository);
        MemberFood findMemberFood = findVerifiedMemberFood(memberFood.getMemberFoodId(),authentication);

        if (!Objects.equals(findMemberFood.getMember(), member)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }
        memberFood.setMember(member);

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

    public MemberFood findMemberFood(long memberFoodId,Authentication authentication) {
        return findVerifiedMemberFood(memberFoodId,authentication);
    }

    public Page<MemberFood> findMemberFoodsSort(int page, int size, Sort sort,Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
        return memberFoodRepository.findAll(PageRequest.of(page, size, sort));
    }

    public List<MemberFood> searchMemberFoodsByKeyword(String keyword,Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
        return memberFoodRepository.findByFood_FoodNameContainingOrMemoContaining(keyword, keyword);
    }

    // 카테고리 내에서 검색
    public Page<MemberFood> searchByCategory(Pageable pageable, String keyword, Food.FoodCategory foodCategory,Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
        Page<Food> foods = foodRepository.findByFoodNameContainingAndFoodCategory(pageable, keyword, foodCategory);

        List<MemberFood> memberFoods = foods.stream()
                .flatMap(food -> memberFoodRepository.findByFood_FoodId(food.getFoodId()).stream())
                .collect(Collectors.toList());


        return new PageImpl<>(memberFoods, pageable, memberFoods.size());
    }

    public void deleteMemberFood(long memberFoodId,Authentication authentication) {
        MemberFood findMemberFood = findMemberFood(memberFoodId,authentication);
        String email = (String) authentication.getPrincipal();
        if(!findMemberFood.getMember().getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }
        memberFoodRepository.delete(findMemberFood);
    }

    public void deleteMultipleMemberFoods(List<Long> ids, Authentication authentication) {
        String email = (String) authentication.getPrincipal();

        // 각 ID에 대해 MemberFood를 찾아서 삭제
        for (Long id : ids) {
            MemberFood findMemberFood = findMemberFood(id, authentication);
            if (!findMemberFood.getMember().getEmail().equals(email)) {
                throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
            }
            memberFoodRepository.delete(findMemberFood);
        }
    }

    public MemberFood findVerifiedMemberFood(long memberFoodId,Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Optional<MemberFood> optionalMemberFood = memberFoodRepository.findById(memberFoodId);
        MemberFood findmemberFood = optionalMemberFood.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_FOOD_NOT_FOUND));
        return findmemberFood;
    }

}
