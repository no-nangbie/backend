package com.nonangbie.menuRecommend.repository;

import com.nonangbie.member.entity.Member;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.menuRecommend.entity.MenuRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRecommendRepository extends JpaRepository<MenuRecommend, Long> {
    void deleteAllByMember(Member member);
    Optional<MenuRecommend> findByMemberAndMenu(Member member, Menu menu);
}
