package com.nonangbie.memberFood.repository;

import com.nonangbie.member.entity.Member;
import com.nonangbie.memberFood.entity.MemberFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberFoodRepository extends JpaRepository<MemberFood, Long> {

    List<MemberFood> findByMember(Member member);

    List<MemberFood> findByFood_FoodNameContainingOrMemoContaining(String foodNameKeyword, String memoKeyword);

    List<MemberFood> findByFood_FoodId(Long foodId);

    Page<MemberFood> findAllByMember(Pageable pageable, Member member);

    List<MemberFood> findAllByMember(Member member);
}
