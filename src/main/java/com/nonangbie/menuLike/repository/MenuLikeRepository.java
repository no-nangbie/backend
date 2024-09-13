package com.nonangbie.menuLike.repository;

import com.nonangbie.member.entity.Member;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.menuLike.entity.MenuLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuLikeRepository extends JpaRepository<MenuLike, Long> {
    Optional<MenuLike> findByMemberAndMenu(Member member, Menu menu);
    Optional<MenuLike> findByMember_emailAndMenu(String memberEmail, Menu menu);
}
