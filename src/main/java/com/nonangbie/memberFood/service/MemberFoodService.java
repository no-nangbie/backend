package com.nonangbie.memberFood.service;

import com.nonangbie.eventListener.CustomEvent;
import com.nonangbie.eventListener.EventCaseEnum;
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
import org.springframework.context.ApplicationEventPublisher;
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
import static com.nonangbie.eventListener.EventCaseEnum.EventCase.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberFoodService extends ExtractMemberEmail {
    private final ApplicationEventPublisher eventPublisher;
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

        //통계 -> 식재료 추가 통계 수집
        CustomEvent event = new CustomEvent(this, STATISTICS_INCREMENT_COUNT,member,"INPUT_FOOD_COUNT",1);
        eventPublisher.publishEvent(event);

        return memberFoodRepository.save(memberFood);
    }

    public MemberFood updateMemberFood(MemberFood memberFood, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        MemberFood findMemberFood = findVerifiedMemberFood(memberFood.getMemberFoodId());

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
                .ifPresent(findMemberFood::setFoodCategory);
        Optional.ofNullable(memberFood.getExpirationDate())
                .ifPresent(findMemberFood::setExpirationDate);
        Optional.ofNullable(memberFood.getMemo())
                .ifPresent(findMemberFood::setMemo);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate expirationDate = LocalDate.parse(findMemberFood.getExpirationDate(), formatter);
        LocalDate currentDate = LocalDate.now();
        long remainingShelfLife = ChronoUnit.DAYS.between(currentDate, expirationDate);

        if (remainingShelfLife >= 0 && remainingShelfLife <= 3) {
            findMemberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Approaching_Expiry);
        } else if (remainingShelfLife < 0) {
            findMemberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Near_Expiry);
        } else {
            findMemberFood.setMemberFoodStatus(MemberFood.MemberFoodStatus.Fresh);
        }

        return memberFoodRepository.save(findMemberFood);
    }


    public MemberFood findMemberFood(long memberFoodId) {
        return findVerifiedMemberFood(memberFoodId);
    }

    public Page<MemberFood> findMemberFoodsSort(int page, int size, Sort sort,Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        return memberFoodRepository.findAllByMember(PageRequest.of(page, size, sort),member);
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
        Member member = extractMemberFromAuthentication(authentication,memberRepository);
        MemberFood findMemberFood = findMemberFood(memberFoodId);
        if (findMemberFood.getMember() != member) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }

        //통계 -> 식재료 삭제 통계 수집
        CustomEvent event = new CustomEvent(this, STATISTICS_INCREMENT_COUNT,member,"OUTPUT_FOOD_COUNT",1);
        eventPublisher.publishEvent(event);
        if(findMemberFood.getMemberFoodStatus() == MemberFood.MemberFoodStatus.Near_Expiry){
            event = new CustomEvent(this, STATISTICS_INCREMENT_COUNT,member,"EXPIRE_FOOD_COUNT",1);
            eventPublisher.publishEvent(event);
        }

        memberFoodRepository.delete(findMemberFood);
    }

    public void deleteMultipleMemberFoods(List<Long> ids, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication,memberRepository);
        CustomEvent event;
        // 각 ID에 대해 MemberFood를 찾아서 삭제
        for (Long id : ids) {
            MemberFood findMemberFood = findMemberFood(id);
            if (findMemberFood.getMember() != member) {
                throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
            }
            if(findMemberFood.getMemberFoodStatus() == MemberFood.MemberFoodStatus.Near_Expiry){
                event = new CustomEvent(this, STATISTICS_INCREMENT_COUNT,member,"EXPIRE_FOOD_COUNT",1);
                eventPublisher.publishEvent(event);
            }
            memberFoodRepository.delete(findMemberFood);
        }

        //통계 -> 식재료 삭제 통계 수집
        event = new CustomEvent(this, STATISTICS_INCREMENT_COUNT,member,"OUTPUT_FOOD_COUNT",ids.size());
        eventPublisher.publishEvent(event);
    }

    // MemberFoodService.java
    public void deleteAllMemberFoods(Authentication authentication) {
        int expireCount = 0;
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        List<MemberFood> memberFoods = memberFoodRepository.findAllByMember(member);
        for(MemberFood memberFood : memberFoods){
            if(memberFood.getMemberFoodStatus() == MemberFood.MemberFoodStatus.Near_Expiry)
                expireCount++;
        }
        CustomEvent event = new CustomEvent(this, STATISTICS_INCREMENT_COUNT,member,"OUTPUT_FOOD_COUNT",memberFoods.size());
        eventPublisher.publishEvent(event);
        event = new CustomEvent(this, STATISTICS_INCREMENT_COUNT,member,"EXPIRE_FOOD_COUNT",expireCount);
        eventPublisher.publishEvent(event);
        memberFoodRepository.deleteAll(memberFoods);
    }


    public MemberFood findVerifiedMemberFood(long memberFoodId) {
        Optional<MemberFood> optionalMemberFood = memberFoodRepository.findById(memberFoodId);
        MemberFood findmemberFood = optionalMemberFood.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_FOOD_NOT_FOUND));
        return findmemberFood;
    }

}
