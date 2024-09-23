package com.nonangbie.statistics.service;

import com.nonangbie.enumData.entity.MenuCookTime;
import com.nonangbie.enumData.entity.MenuDifficulty;
import com.nonangbie.enumData.repository.MenuCookTimeRepository;
import com.nonangbie.enumData.repository.MenuDifficultyRepository;
import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.menu.repository.MenuRepository;
import com.nonangbie.statistics.entity.Statistics;
import com.nonangbie.statistics.repository.StatisticsRepository;
import com.nonangbie.enumData.entity.MenuCategory;
import com.nonangbie.enumData.repository.MenuCategoryRepository;
import com.nonangbie.utils.ExtractMemberEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsService extends ExtractMemberEmail {
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuDifficultyRepository menuDifficultyRepository;
    private final MenuCookTimeRepository menuCookTimeRepository;
    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final StatisticsRepository repository;

    public List<Statistics> createStatistics(Member saveMember) {
        List<MenuCategory> allCategories = menuCategoryRepository.findAll();
        List<MenuDifficulty> allDifficulty = menuDifficultyRepository.findAll();
        List<MenuCookTime> allCookTime = menuCookTimeRepository.findAll();
        List<String> countStatisticsList = new ArrayList<>();
        countStatisticsList.add("FOOD_MANAGER_SCORE");
        countStatisticsList.add("INPUT_FOOD_COUNT");
        countStatisticsList.add("OUTPUT_FOOD_COUNT");
        countStatisticsList.add("EXPIRE_FOOD_COUNT");
        countStatisticsList.add("COOK_COUNT");
        List<Statistics> statisticsList = new ArrayList<>();

        for (MenuCategory menuCategory : allCategories) {
            Statistics statistics = new Statistics();
            statistics.setDescription(menuCategory.getCode());
            statistics.setMember(saveMember);
            statistics.setMenuCategory(menuCategory);
            statistics.setMenuCookTime(null);
            statistics.setMenuDifficulty(null);
            statistics.setInfoType(Statistics.DType.CATEGORY);
            statisticsList.add(statistics);
        }
        for (MenuDifficulty menuDifficulty : allDifficulty) {
            Statistics statistics = new Statistics();
            statistics.setDescription(menuDifficulty.getCode());
            statistics.setMember(saveMember);
            statistics.setMenuCategory(null);
            statistics.setMenuCookTime(null);
            statistics.setMenuDifficulty(menuDifficulty);
            statistics.setInfoType(Statistics.DType.DIFFICULTY);
            statisticsList.add(statistics);
        }
        for (MenuCookTime menuCookTime : allCookTime) {
            Statistics statistics = new Statistics();
            statistics.setDescription(menuCookTime.getCode());
            statistics.setMember(saveMember);
            statistics.setMenuCategory(null);
            statistics.setMenuCookTime(menuCookTime);
            statistics.setMenuDifficulty(null);
            statistics.setInfoType(Statistics.DType.COOKTIME);
            statisticsList.add(statistics);
        }
        for (String string : countStatisticsList) {
            Statistics statistics = new Statistics();
            statistics.setDescription(string);
            statistics.setMember(saveMember);
            statistics.setMenuCategory(null);
            statistics.setMenuCookTime(null);
            statistics.setMenuDifficulty(null);
            statistics.setInfoType(Statistics.DType.COUNT);
            statisticsList.add(statistics);
        }
        return repository.saveAll(statisticsList);
    }

    public boolean increaseCookCount(Long menuId, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        Menu findMenu = menuRepository.findById(menuId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MENU_NOT_FOUND));
        String MenuCategoryCode = String.valueOf(findMenu.getMenuCategory());
        String MenuDifficultyCode = String.valueOf(findMenu.getDifficulty());
        String MenuCookTime;
        if (findMenu.getCookingTime() >= 240)
            MenuCookTime = "4HOURS_OVER";
        else if (findMenu.getCookingTime() >= 120)
            MenuCookTime = "2HOURS_TO_4HOURS";
        else if (findMenu.getCookingTime() >= 60)
            MenuCookTime = "1HOURS_TO_2HOURS";
        else
            MenuCookTime = "0_TO_1HOURS";

        List<Statistics> findStatistics = new ArrayList<>();
        findStatistics.add(repository.findByMemberAndMenuCategory_Code(member, MenuCategoryCode)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCESS_DENIED)));
        findStatistics.add(repository.findByMemberAndMenuDifficulty_Code(member, MenuDifficultyCode)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCESS_DENIED)));
        findStatistics.add(repository.findByMemberAndMenuCookTime_Code(member, MenuCookTime)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCESS_DENIED)));
        findStatistics.add(repository.findByMemberAndDescription(member, "COOK_COUNT")
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCESS_DENIED)));
        for (Statistics statistics : findStatistics) {
            statistics.setCount(statistics.getCount() + 1);
            repository.save(statistics);
        }
        return true;
    }

    public void increaseCount(Member member, String description, int count) {
        Statistics findStatistics = repository.findByMemberAndDescription(member, description)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATISTICS_NOT_FOUND));
        findStatistics.setCount(findStatistics.getCount() + count);
    }

    public int calculatorScore(Member member) {
        Statistics findInputStatistics = repository.findByMemberAndDescription(member, "INPUT_FOOD_COUNT")
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATISTICS_NOT_FOUND));
        Statistics findOutputStatistics = repository.findByMemberAndDescription(member, "OUTPUT_FOOD_COUNT")
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATISTICS_NOT_FOUND));
        Statistics findExpireStatistics = repository.findByMemberAndDescription(member, "EXPIRE_FOOD_COUNT")
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATISTICS_NOT_FOUND));
        float minusPoint = 0;
        int outputScore = findOutputStatistics.getCount();
        int outputScoreAdd = findOutputStatistics.getCount();
        int expireScore = findExpireStatistics.getCount();
        int inputScore = (int) (findInputStatistics.getCount() * 0.25);
        if(inputScore > 20)
            inputScore = 20;
        if(outputScoreAdd > 80)
            outputScoreAdd = 80;
        if(expireScore > 0 && outputScore > 0)
            minusPoint = (float) expireScore / outputScore * outputScoreAdd;
        return (inputScore + outputScoreAdd - (int)minusPoint);
    }

    public List<Statistics> findVerifiedStatistics(String page, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        if(page.equals("fridge")) {
            Statistics findstatistics = repository.findByMemberAndDescription(member, "FOOD_MANAGER_SCORE")
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATISTICS_NOT_FOUND));
            findstatistics.setCount(calculatorScore(member));
            repository.save(findstatistics);
        }
        return repository.findAllByMember(member);
    }

    public void clearStatistics(Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        List<Statistics> StatisticsList = repository.findAllByMember(member);
        for (Statistics statistics : StatisticsList) {
            statistics.setCount(0);
            repository.save(statistics);
        }
    }
}
