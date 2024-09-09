package com.nonangbie.board.dto;

import com.nonangbie.board.entity.Board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

public class BoardDto{
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]+$", message = "제목은 영문자,숫자,공백,한글만 허용됩니다")
        private String title;

        @NotBlank
        @Size(min = 1, max = 255, message = "1자에서 255자 이내로 작성 가능합니다")
        private String boardContent;

        @NotBlank
        @Size(min = 1, max = 5000, message = "1자에서 5000자 이내로 작성 가능합니다")
        private String foodContent;

        @NotBlank
        @Size(min = 1, max = 5000, message = "1자에서 5000자 이내로 작성 가능합니다")
        private String recipeContent;

        @NotBlank
        private String imageUrl;

        @NotNull
        private int cookingTime;

        @NotNull
        private int servingSize;

        @NotNull
        private Board.MenuCategory boardMenuCategory;

        @NotNull
        private Board.Difficulty difficulty;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {

        private long boardId;

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]+$", message = "제목은 영문자,숫자,공백,한글만 허용됩니다")
        private String title;

        @NotBlank
        @Size(min = 1, max = 255, message = "1자에서 255자 이내로 작성 가능합니다")
        private String boardContent;

        @NotBlank
        @Size(min = 1, max = 5000, message = "1자에서 5000자 이내로 작성 가능합니다")
        private String foodContent;

        @NotBlank
        @Size(min = 1, max = 5000, message = "1자에서 5000자 이내로 작성 가능합니다")
        private String recipeContent;

        @NotBlank
        private String imageUrl;

        @NotNull
        private int cookingTime;

        @NotNull
        private int servingSize;

        @NotNull
        private Board.MenuCategory boardMenuCategory;

        @NotNull
        private Board.Difficulty difficulty;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Responses {
        private long boardId;
        private String title;
        private String author;
        private String imageUrl;
        private String menuCategory;
        private int cookingTime;
        private int servingSize;
        private int likesCount;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private long boardId;
        private String author;
        private String title;
        private String boardContent;
        private String foodContent;
        private String recipeContent;
        private String imageUrl;
        private int cookingTime;
        private int servingSize;
        private int likesCount;
        private String MenuCategory;
        private String difficulty;
    }
}
