package com.nonangbie.memberMenuCategory.repository;

import com.nonangbie.member.entity.Member;
import com.nonangbie.memberMenuCategory.entity.MemberMenuCategory;
import com.nonangbie.menuCategory.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MemberMenuCategoryRepository extends JpaRepository<MemberMenuCategory, Long> {

    List<MemberMenuCategory> findByMember(Member member);

    Optional<MemberMenuCategory> findByMemberAndMenuCategory(Member member, MenuCategory menuCategory);

}

