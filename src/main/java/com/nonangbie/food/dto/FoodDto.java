package com.nonangbie.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.nonangbie.food.entity.Food.FoodCategory;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FoodDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        @NotBlank
        private String foodName;

        @NotNull
        private FoodCategory foodCategory;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private long foodId;

        private String foodName;

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
    }
}
