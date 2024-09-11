package com.nonangbie.menu.reposiitory;

import com.nonangbie.memberFood.entity.MemberFood;
import com.nonangbie.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Page<Menu> findByMenuCategory(Pageable pageable, Menu.MenuCategory menuCategory);

    List<Menu> findByMenuTitleContainingOrMenuDescriptionContaining(String menuTitleKeyword, String menuDescriptionKeyword);

    @Query("SELECT m FROM Menu m WHERE (m.menuTitle LIKE %:keyword%) AND m.menuCategory = :menuCategory")
    Page<Menu> searchByMenuTitleAndCategory(Pageable pageable, @Param("keyword") String keyword, @Param("menuCategory") Menu.MenuCategory menuCategory);
}
