package com.nonangbie.menu.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nonangbie.audit.Auditable;
import com.nonangbie.foodMenu.entity.FoodMenu;
import com.nonangbie.utils.RecipesConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Menu extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuId;

    @Column(nullable = false)
    private String menuTitle;

    @Column(nullable = false)
    private String menuDescription;

//    @ElementCollection
    @Convert(converter = RecipesConverter.class)
    @Column(nullable = false, columnDefinition = "TEXT")  // JSON 데이터는 길이가 길 수 있으므로 TEXT로 정의
    private List<String> recipes = new ArrayList<>();

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuCategory menuCategory = MenuCategory.MENU_CATEGORY_ETC;

    @Column(nullable = false)
    private int cookingTime;

    @Column(nullable = false)
    private int servingSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty = Difficulty.DIFFICULTY_EASY;

    @Column(nullable = false)
    private int menuLikeCount = 0;

    @Column(nullable = false)
    private int missingFoodsCount;

    public enum MenuCategory {
        MENU_CATEGORY_SIDE("밑 반찬"),
        MENU_CATEGORY_SOUP("국/찌개"),
        MENU_CATEGORY_DESSERT("디저트"),
        MENU_CATEGORY_NOODLE("면"),
        MENU_CATEGORY_RICE("밥/죽/떡"),
        MENU_CATEGORY_KIMCHI("김치"),
        MENU_CATEGORY_FUSION("퓨전"),
        MENU_CATEGORY_SEASONING("양념"),
        MENU_CATEGORY_WESTERN("양식"),
        MENU_CATEGORY_ETC("기타");

        @Getter
        private String status;

        MenuCategory(String status) {
            this.status = status;
        }
    }

    public enum Difficulty {
        DIFFICULTY_EASY("쉬움"),
        DIFFICULTY_MEDIUM("보통"),
        DIFFICULTY_HARD("어려움");

        @Getter
        private String status;

        Difficulty(String status) {
            this.status = status;
        }
    }

    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<FoodMenu> foodMenuList = new ArrayList<>();
}
