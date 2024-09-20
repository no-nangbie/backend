package com.nonangbie.AllergyFood.Service;

import com.nonangbie.AllergyFood.Entity.AllergyFood;
import com.nonangbie.AllergyFood.Repository.AllergyFoodRepository;
import com.nonangbie.eventListener.CustomEvent;
import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.food.entity.Food;
import com.nonangbie.food.repository.FoodRepository;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.utils.ExtractMemberEmail;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AllergyFoodService extends ExtractMemberEmail {
    private final ApplicationEventPublisher eventPublisher;
    private final AllergyFoodRepository allergyFoodRepository;
    private final FoodRepository foodRepository;
    private final MemberRepository memberRepository;

    public AllergyFood createAllergyFood(AllergyFood allergyFood, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        Food findFood = foodRepository.findByFoodName(allergyFood.getFood().getFoodName())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOOD_NOT_FOUND));
        allergyFood.setFood(findFood);
        allergyFood.setMember(member);
        allergyFood.setFoodName(findFood.getFoodName());

        return allergyFoodRepository.save(allergyFood);
    }


    public AllergyFood updateAllergyFood(AllergyFood allergyFood, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        AllergyFood findAllergyFood = findVerifiedAllergyFood(allergyFood.getAllergyFoodId());

        if (!Objects.equals(findAllergyFood.getMember(), member)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }
        allergyFood.setMember(member);

        Optional.ofNullable(allergyFood.getFood())
                .map(Food::getFoodName)
                .ifPresent(foodName -> {
                    Food findFood = foodRepository.findByFoodName(foodName)
                            .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOOD_NOT_FOUND));
                    findAllergyFood.setFood(findFood);
                });

        Optional.ofNullable(allergyFood.getFoodCategory())
                .ifPresent(findAllergyFood::setFoodCategory);

        return allergyFoodRepository.save(findAllergyFood);
    }

    public AllergyFood findAllergyFood(long allergyFoodId) {
        return findVerifiedAllergyFood(allergyFoodId);
    }

    public Page<AllergyFood> findAllergyFoods(int page, int size, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        return allergyFoodRepository.findAllByMember(PageRequest.of(page, size), member);
    }


    public AllergyFood findVerifiedAllergyFood(long allergyFoodId) {
        Optional<AllergyFood> optionalAllergyFood = allergyFoodRepository.findById(allergyFoodId);
        AllergyFood findAllergyFood = optionalAllergyFood.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ALLERGY_Food_FOOD_NOT_FOUND));
        return findAllergyFood;
    }

    public void deleteAllergyFood(long allergyFoodId, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        AllergyFood findAllergyFood = findAllergyFood(allergyFoodId);
        if (findAllergyFood.getMember() != member) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }
        allergyFoodRepository.delete(findAllergyFood);

    }

    public void deleteMultipleAllergyFoods(List<Long> ids, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        CustomEvent event;

        for (Long id : ids) {
            AllergyFood findAllergyFood = findAllergyFood(id);
            if (findAllergyFood.getMember() != member) {
                throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
            }
            allergyFoodRepository.delete(findAllergyFood);
        }
    }

}
