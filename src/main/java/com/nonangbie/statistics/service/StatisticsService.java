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

    public List<Statistics> createStatistics(Member saveMember){
        List<MenuCategory> allCategories = menuCategoryRepository.findAll();
        List<MenuDifficulty> allDifficulty = menuDifficultyRepository.findAll();
        List<MenuCookTime> allCookTime = menuCookTimeRepository.findAll();
        List<Statistics> statisticsList = new ArrayList<>();

        for(MenuCategory menuCategory : allCategories){
            Statistics statistics = new Statistics();
            statistics.setMember(saveMember);
            statistics.setMenuCategory(menuCategory);
            statistics.setMenuCookTime(null);
            statistics.setMenuDifficulty(null);
            statistics.setInfoType(Statistics.DType.CA);
            statisticsList.add(statistics);
        }
        for(MenuDifficulty menuDifficulty : allDifficulty){
            Statistics statistics = new Statistics();
            statistics.setMember(saveMember);
            statistics.setMenuCategory(null);
            statistics.setMenuCookTime(null);
            statistics.setMenuDifficulty(menuDifficulty);
            statistics.setInfoType(Statistics.DType.DI);
            statisticsList.add(statistics);
        }
        for(MenuCookTime menuCookTime : allCookTime){
            Statistics statistics = new Statistics();
            statistics.setMember(saveMember);
            statistics.setMenuCategory(null);
            statistics.setMenuCookTime(menuCookTime);
            statistics.setMenuDifficulty(null);
            statistics.setInfoType(Statistics.DType.CO);
            statisticsList.add(statistics);
        }
        return repository.saveAll(statisticsList);
    }

    public boolean incrementCount(Long menuId, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication,memberRepository);
        Menu findMenu = menuRepository.findById(menuId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MENU_NOT_FOUND));
        String MenuCategoryCode = String.valueOf(findMenu.getMenuCategory());
        String MenuDifficultyCode = String.valueOf(findMenu.getDifficulty());
        String MenuCookTime;
        if(findMenu.getCookingTime() >= 240 )
            MenuCookTime = "4HOURS_OVER";
        else if(findMenu.getCookingTime() >= 120)
            MenuCookTime = "2HOURS_TO_4HOURS";
        else if(findMenu.getCookingTime() >= 60)
            MenuCookTime = "1HOURS_TO_2HOURS";
        else
            MenuCookTime = "0_TO_1HOURS";

        List<Statistics> findStatistics = new ArrayList<>();
        findStatistics.add(repository.findByMemberAndMenuCategory_Code(member,MenuCategoryCode)
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.ACCESS_DENIED)));
        findStatistics.add(repository.findByMemberAndMenuDifficulty_Code(member,MenuDifficultyCode)
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.ACCESS_DENIED)));
        findStatistics.add(repository.findByMemberAndMenuCookTime_Code(member,MenuCookTime)
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.ACCESS_DENIED)));
        for(Statistics statistics : findStatistics){
            statistics.setCount(statistics.getCount()+1);
            repository.save(statistics);
        }
        return true;
    }
}
