package com.nonangbie.memberFood.repository;

import com.nonangbie.member.entity.Member;
import com.nonangbie.memberFood.entity.MemberFood;
import com.nonangbie.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberFoodRepository extends JpaRepository<MemberFood, Long> {

    List<MemberFood> findByMember(Member member);

    @Query("SELECT m FROM MemberFood m WHERE m.member = :member AND (m.food.foodName LIKE %:foodNameKeyword% " +
            "OR m.memo LIKE %:memoKeyword%)")
    List<MemberFood> findByFood_FoodNameContainingOrMemoContaining(@Param("foodNameKeyword") String foodNameKeyword,
                                                                   @Param("memoKeyword") String memoKeyword,
                                                                   @Param("member") Member member);
    List<MemberFood> findByFood_FoodId(Long foodId);

    Page<MemberFood> findAllByMember(Pageable pageable, Member member);

    List<MemberFood> findAllByMember(Member member);
}
