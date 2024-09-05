package com.nonangbie.menu.dto;

import com.nonangbie.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class MenuDto {

    @Getter
    @AllArgsConstructor
    public static class Post{

        @Pattern(regexp = "^.{1,20}$", message = "메뉴 제목은 1~20 글자 이어야 합니다.")
        private String menuTitle;

        @Pattern(regexp = "^.{1,255}$", message = "메뉴 설명은 1~255 글자 이어야 합니다.")
        private String menuDescription;

        private Menu.MenuCategory menuCategory;

        private List<String> recipes;

        @Min(value = 0, message = "요리 시간은 분(minutes) 단위이며  0 이상이어야 합니다.")
        private int cookingTime;

        @Range(min = 1, max = 10, message = "음식량은 1~10인분 이어야 합니다.")
        private int servingSize;

        private Menu.Difficulty difficulty;

        @Pattern(regexp = "^(http|https)://.*$", message = "Image URL must be a valid URL.")
        private String imageUrl;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class Patch{

        private long menuId;

        @Pattern(regexp = "^.{1,20}$", message = "메뉴 제목은 1~20 글자 이어야 합니다.")
        private String menuTitle;

        @Pattern(regexp = "^.{1,255}$", message = "메뉴 설명은 1~255 글자 이어야 합니다.")
        private String menuDescription;

        private Menu.MenuCategory menuCategory;

        private List<String> recipes;

        @Pattern(regexp = "^.{1,5}$", message = "요리 시간은 1~5 글자 이어야 합니다.")
        private int cookingTime;

        @Pattern(regexp = "^.{1,15}$", message = "음식량은 1~5 글자 이어야 합니다.")
        private int servingSize;

        private Menu.Difficulty difficulty;

        @Pattern(regexp = "^(http|https)://.*$", message = "Image URL must be a valid URL.")
        private String imageUrl;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Reponse{
        private long menuId;
        private String menuTitle;
        private String menuDescription;
        private Menu.MenuCategory menuCategory;
        private int cookingTime;
        private int servingSize;
        private Menu.Difficulty difficulty;
        private List<String> recipes;
        private int menuLikeCount;
    }
}
