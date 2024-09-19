package com.nonangbie.enumData.repository;

import com.nonangbie.enumData.entity.MenuCookTime;
import com.nonangbie.enumData.entity.MenuDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuCookTimeRepository extends JpaRepository<MenuCookTime, Long> {
    Optional<MenuCookTime> findByCode(String code);
}

