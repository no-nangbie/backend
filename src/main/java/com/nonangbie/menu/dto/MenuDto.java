package com.nonangbie.menu.dto;

import com.nonangbie.foodMenu.dto.FoodMenuDto;
import com.nonangbie.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class MenuDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post{

        @Pattern(regexp = "^.{1,20}$", message = "메뉴 제목은 1~20 글자 이어야 합니다.")
        private String menuTitle;

        @Pattern(regexp = "^.{1,255}$", message = "메뉴 설명은 1~255 글자 이어야 합니다.")
        private String menuDescription;

        private Menu.MenuCategory menuCategory;

        private List<String> recipes;

        @Pattern(regexp = "^.{1,5}$", message = "요리 시간은 1~5 글자 이어야 합니다.")
        private String cookingTime;

        @Pattern(regexp = "^.{1,15}$", message = "음식량은 1~5 글자 이어야 합니다.")
        private String servingSize;

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
        private String cookingTime;

        @Pattern(regexp = "^.{1,15}$", message = "음식량은 1~5 글자 이어야 합니다.")
        private String servingSize;

        private Menu.Difficulty difficulty;

        @Pattern(regexp = "^(http|https)://.*$", message = "Image URL must be a valid URL.")
        private String imageUrl;

    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        private String menuTitle;
        private String menuDescription;
        private Menu.MenuCategory menuCategory;
        private String cookingTime;
        private String servingSize;
        private Menu.Difficulty difficulty;
        private List<String> recipes;
        private List<FoodMenuDto.Response> foodMenuQuantityList;
    }
}
