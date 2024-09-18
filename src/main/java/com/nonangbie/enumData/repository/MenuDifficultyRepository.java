package com.nonangbie.enumData.repository;

import com.nonangbie.enumData.entity.MenuCategory;
import com.nonangbie.enumData.entity.MenuDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuDifficultyRepository extends JpaRepository<MenuDifficulty, Long> {
    Optional<MenuDifficulty> findByCode(String code);
}

