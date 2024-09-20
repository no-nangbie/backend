package com.nonangbie.AllergyFood.Repository;

import com.nonangbie.AllergyFood.Entity.AllergyFood;
import com.nonangbie.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllergyFoodRepository extends JpaRepository<AllergyFood, Long> {

    List<AllergyFood> findByMember(Member member);

    List<AllergyFood> findByFood_FoodId(Long foodId);

    Page<AllergyFood> findAllByMember(Pageable pageable, Member member);

    List<AllergyFood> findAllByMember(Member member);
}
