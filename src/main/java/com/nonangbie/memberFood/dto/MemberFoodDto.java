package com.nonangbie.memberFood.dto;

import com.nonangbie.food.entity.Food;
import com.nonangbie.memberFood.entity.MemberFood;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MemberFoodDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        @NotBlank
        private String foodName;

        @NotNull
        private Food.FoodCategory foodCategory;

        @NotBlank
        @Pattern(regexp = "\\d{8}", message = "유통기한은 숫자 8자리로 입력해주세요 (예: 20240101)")
        private String expirationDate;

        private String memo;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {

        private long memberFoodId;

        private String foodName;

        private Food.FoodCategory foodCategory;

        @Pattern(regexp = "\\d{8}", message = "유통기한은 숫자 8자리로 입력해주세요 (예: 20240101)")
        private String expirationDate;

        private String memo;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private long memberFoodId;
        private long memberId;
        private String foodName;
        private Food.FoodCategory foodCategory;
        private String expirationDate;
        private String memo;
        private MemberFood.MemberFoodStatus memberFoodStatus;
    }
}
