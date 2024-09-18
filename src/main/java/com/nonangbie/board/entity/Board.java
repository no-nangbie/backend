package com.nonangbie.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nonangbie.audit.Auditable;
import com.nonangbie.boardLike.entity.BoardLike;
import com.nonangbie.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Board extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @Column(name = "board_title", nullable = false)
    private String title;

    @Column(name = "board_content",columnDefinition = "Text")
    private String boardContent;

    @Column(name = "food_content",columnDefinition = "Text")
    private String foodContent;

    @Column(name = "recipe_content",columnDefinition = "Text")
    private String recipeContent;

    @Column(name = "iamge_url")
    private String imageUrl;

    @Column(name = "board_category")
    @Enumerated(value = EnumType.STRING)
    private MenuCategory menuCategory;

    @Column(name = "cooking_time")
    private int cookingTime;

    @Column(name = "serving_size")
    private int servingSize;

    @Column(name = "difficulty")
    private Difficulty difficulty = Difficulty.DIFFICULTY_EASY;

    @Column(name = "board_like_count")
    private int likeCount = 0;

    @OneToMany(mappedBy = "board",cascade = {CascadeType.REMOVE})
    @JsonManagedReference
    private List<BoardLike> boardLikeList = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @AllArgsConstructor
    @NoArgsConstructor
    public enum MenuCategory{
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
