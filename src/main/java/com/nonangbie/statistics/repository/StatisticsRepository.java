package com.nonangbie.statistics.repository;

import com.nonangbie.member.entity.Member;
import com.nonangbie.statistics.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    @Query("SELECT s FROM Statistics s " +
            "WHERE s.member = :member " +
            "AND s.menuCategory.code = :menuCategoryCode")
    Optional<Statistics> findByMemberAndMenuCategory_Code(
            @Param("member") Member member,
            @Param("menuCategoryCode") String menuCategoryCode);

    @Query("SELECT s FROM Statistics s " +
            "WHERE s.member = :member " +
            "AND s.menuDifficulty.code = :menuDifficultyCode")
    Optional<Statistics> findByMemberAndMenuDifficulty_Code(
            @Param("member") Member member,
            @Param("menuDifficultyCode") String menuDifficultyCode);

    @Query("SELECT s FROM Statistics s " +
            "WHERE s.member = :member " +
            "AND s.menuCookTime.code = :menuCookTimeCode")
    Optional<Statistics> findByMemberAndMenuCookTime_Code(
            @Param("member") Member member,
            @Param("menuCookTimeCode") String menuCookTimeCode);
//    Optional<Statistics> findByMemberAndMenuCategory_Code(Member member, String menuCategoryCode);
//    Optional<Statistics> findByMemberAndMenuDifficulty_Code(Member member, String menuDifficultyCode);
//    Optional<Statistics> findByMemberAndMenuCookTime_Code(Member member, String menuCookTimeCode);

    @Query(value = "(SELECT * FROM statistics s WHERE s.member = :member AND s.menuCategory_code = :menuCategoryCode) " +
            "UNION ALL " +
            "(SELECT * FROM statistics s WHERE s.member = :member AND s.menuDifficulty_code = :menuDifficultyCode) " +
            "UNION ALL " +
            "(SELECT * FROM statistics s WHERE s.member = :member AND s.menuCookTime_code = :menuCookTimeCode)",
            nativeQuery = true)
    List<Statistics> findAllByMemberAndCodes(
            @Param("member") Member member,
            @Param("menuCategoryCode") String menuCategoryCode,
            @Param("menuDifficultyCode") String menuDifficultyCode,
            @Param("menuCookTimeCode") String menuCookTimeCode);

}

