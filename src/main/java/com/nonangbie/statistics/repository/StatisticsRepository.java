package com.nonangbie.statistics.repository;

import com.nonangbie.member.entity.Member;
import com.nonangbie.statistics.entity.Statistics;
import com.nonangbie.enumData.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Optional<Statistics> findByMemberAndInfoTypeAndMenuCategory_Code(Member member, Statistics.DType infoType, String menuCategoryCode);
    Optional<Statistics> findByMemberAndInfoTypeAndMenuDifficulty_Code(Member member, Statistics.DType infoType, String menuDifficultyCode);
    Optional<Statistics> findByMemberAndInfoTypeAndMenuCookTime_Code(Member member, Statistics.DType infoType, String menuCookTimeCode);

//    List<Statistics> findAllByMemberAndMenuCategory_CodeORMenuDifficulty_CodeORMenuCookTime_Code(Member member, String Code1, String Code2, String Code3);

}

