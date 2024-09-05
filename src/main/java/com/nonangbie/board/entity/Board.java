package com.nonangbie.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nonangbie.audit.Auditable;
import com.nonangbie.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Board extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @Column(name = "BOARD_TITLE", nullable = false)
    private String title;

    @Column(name = "BOARD_CONTENT",columnDefinition = "Text")
    private String boardContent;

    @Column(name = "FOOD_CONTENT",columnDefinition = "Text")
    private String foodContent;

    @Column(name = "RECIPE_CONTENT",columnDefinition = "Text")
    private String recipeContent;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "BOARD_CATEGORY")
    @Enumerated(value = EnumType.STRING)
    private MenuCategory menuCategory;

    @Column(name = "COOKING_TIME")
    private int cookingTime;

    @Column(name = "SERVING_SIZE")
    private int servingSize;

    @Column(name = "DIFFICULTY")
    private Difficulty difficulty = Difficulty.DIFFICULTY_EASY;

    @Column(name = "BOARD_LIKE_COUNT")
    private int likeCount = 0;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @AllArgsConstructor
    public enum MenuCategory{
        MENU_CATEGORY_SIDE("밑 반찬"),
        MENU_CATEGORY_SOUP("국/찌개"),
        MENU_CATEGORY_DESSERT("디저트"),
        MENU_CATEGORY_NOODLE("면"),
        MENU_CATEGORY_RICE("밥/죽/떡"),
        MENU_CATEGORY_KIMCHI("김치/젓갈/장류"),
        MENU_CATEGORY_FUSION("퓨전"),
        MENU_CATEGORY_SEASONING("양념"),
        MENU_CATEGORY_WESTERN("양식"),
        MENU_CATEGORY_ETC("기타");

        @Getter
        @Setter
        private String status;
    }

    @AllArgsConstructor
    public enum Difficulty{
        DIFFICULTY_EASY("쉬움"),
        DIFFICULTY_MEDIUM("보통"),
        DIFFICULTY_HARD("어려움");

        @Getter
        @Setter
        private String status;
    }
}
