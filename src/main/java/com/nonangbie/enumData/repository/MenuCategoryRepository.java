package com.nonangbie.enumData.repository;
import com.nonangbie.enumData.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    Optional<MenuCategory> findByCode(String code);
}

