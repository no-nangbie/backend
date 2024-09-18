package com.nonangbie.statistics.service;

import com.nonangbie.enumData.entity.MenuCookTime;
import com.nonangbie.enumData.entity.MenuDifficulty;
import com.nonangbie.enumData.repository.MenuCookTimeRepository;
import com.nonangbie.enumData.repository.MenuDifficultyRepository;
import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.entity.Member;
import com.nonangbie.statistics.entity.Statistics;
import com.nonangbie.statistics.repository.StatisticsRepository;
import com.nonangbie.enumData.entity.MenuCategory;
import com.nonangbie.enumData.repository.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsService {
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuDifficultyRepository menuDifficultyRepository;
    private final MenuCookTimeRepository menuCookTimeRepository;
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

//    public void incrementCount(Member member, String MenuCategoryCode, String MenuDifficultyCode, String MenuCookTime) {
//        List<Statistics> findStatistics = repository.findAllByMemberAndMenuCategory_CodeORMenuDifficulty_CodeORMenuCookTime_Code(
//                member, MenuCategoryCode, MenuDifficultyCode,MenuCookTime);
//        for(Statistics statistics : findStatistics){
//            statistics.setCount(statistics.getCount()+1);
//            repository.save(statistics);
//        }
//    }

    public void variationCount(Member member, String Code, Statistics.DType dType, String variation) {
        Statistics findStatistics;
        switch (dType){
            case CA:
                findStatistics = repository.findByMemberAndInfoTypeAndMenuCategory_Code(member, dType, Code)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATISTICS_NOT_FOUND));
                if(variation.equals("increment"))
                    findStatistics.setCount(findStatistics.getCount()+1);
                else
                    findStatistics.setCount(findStatistics.getCount()-1);
                repository.save(findStatistics); break;
            case CO:
                findStatistics = repository.findByMemberAndInfoTypeAndMenuDifficulty_Code(member, dType, Code)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATISTICS_NOT_FOUND));
                if(variation.equals("increment"))
                    findStatistics.setCount(findStatistics.getCount()+1);
                else
                    findStatistics.setCount(findStatistics.getCount()-1);
                repository.save(findStatistics); break;
            case DI:
                findStatistics = repository.findByMemberAndInfoTypeAndMenuCookTime_Code(member, dType, Code)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATISTICS_NOT_FOUND));
                if(variation.equals("increment"))
                    findStatistics.setCount(findStatistics.getCount()+1);
                else
                    findStatistics.setCount(findStatistics.getCount()-1);
                repository.save(findStatistics); break;
        }
    }
}
