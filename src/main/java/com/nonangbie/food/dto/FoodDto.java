package com.nonangbie.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.nonangbie.food.entity.Food.FoodCategory;
import com.nonangbie.food.entity.Food.FoodStatus;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FoodDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private String foodName;

        @NotNull
        private FoodCategory foodCategory;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        @Setter
        private long foodId;

        @NotBlank
        private String foodName;

        @NotBlank
        private FoodCategory foodCategory;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private long foodId;
        private String foodName;
        private FoodCategory foodCategory;
        private FoodStatus foodStatus;
    }
}
