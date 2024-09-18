package com.nonangbie.menuCategory.repository;
import com.nonangbie.menuCategory.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    Optional<MenuCategory> findByCode(String code);
}

