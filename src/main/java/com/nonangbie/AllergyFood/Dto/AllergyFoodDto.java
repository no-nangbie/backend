package com.nonangbie.AllergyFood.Dto;

import com.nonangbie.food.entity.Food;
import com.nonangbie.memberFood.entity.MemberFood;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AllergyFoodDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        @NotBlank
        private String foodName;

        @NotNull
        private Food.FoodCategory foodCategory;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {

        private long allergyFoodId;

        private String foodName;

        private Food.FoodCategory foodCategory;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private long allergyFoodId;
        private long memberId;
        private String foodName;
        private Food.FoodCategory foodCategory;
    }
}
