package com.nonangbie.menu.repository;

import com.nonangbie.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Page<Menu> findByMenuCategory(Pageable pageable, Menu.MenuCategory menuCategory);

    List<Menu> findByMenuTitleContainingOrMenuDescriptionContaining(String menuTitleKeyword, String menuDescriptionKeyword);

    @Query("SELECT m FROM Menu m WHERE (m.menuTitle LIKE %:keyword%) AND m.menuCategory = :menuCategory")
    Page<Menu> searchByMenuTitleAndCategory(Pageable pageable, @Param("keyword") String keyword, @Param("menuCategory") Menu.MenuCategory menuCategory);

    @Query("SELECT m FROM Menu m WHERE "
            + "(:keyword IS NULL OR m.menuTitle LIKE CONCAT('%', :keyword, '%')) AND "
            + "(:menuCategory IS NULL OR m.menuCategory = :menuCategory) AND "
            + "(:foodId IS NULL OR EXISTS (SELECT n FROM FoodMenu n WHERE n.menu = m AND n.food.foodId = :foodId))")
    Page<Menu> findAllMenusIntegration(Pageable pageable,
                                       @Param("menuCategory") Menu.MenuCategory menuCategory,
                                       @Param("keyword") String keyword,
                                       @Param("foodId") Long foodId); // foodId 타입을 Long으로 변경

//    @Query("SELECT m FROM Menu m " +
//            "WHERE (:menuTitles IS NULL OR NOT EXISTS (" +
//            "SELECT 1 FROM Menu mt WHERE " +
//            "(mt.menuTitle LIKE CONCAT('%', :menuTitle, '%')) " +
//            "AND mt.menuId = m.menuId)) " +
//            "AND (:menuCategoryList IS NULL OR m.menuCategory IN :menuCategoryList)")
//    List<Menu> findAllByRecommendations(@Param("menuTitles") List<String> menuTitles,
//                                        @Param("menuCategoryList") List<Menu.MenuCategory> menuCategoryList);


    @Query("SELECT m FROM Menu m " +
            "WHERE (:menuCategoryList IS NULL OR m.menuCategory IN :menuCategoryList)")
    List<Menu> findAllByRecommendations(@Param("menuCategoryList") List<Menu.MenuCategory> menuCategoryList);



}
