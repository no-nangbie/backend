package com.nonangbie.statistics.dto;

import com.nonangbie.enumData.entity.MenuCategory;
import com.nonangbie.enumData.entity.MenuCookTime;
import com.nonangbie.enumData.entity.MenuDifficulty;
import com.nonangbie.food.entity.Food;
import com.nonangbie.memberFood.entity.MemberFood;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class StatisticsDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FridgeResponse {
        private int foodManagerScore;
        private int inputFoodCount;
        private int outputFoodCount;
        private int ExpireFoodCount;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeResponse {
        private int menuCategorySide;
        private int menuCategorySoup;
        private int menuCategoryDessert;
        private int menuCategoryNoodle;
        private int menuCategoryRice;
        private int menuCategoryKimchi;
        private int menuCategoryFusion;
        private int menuCategorySeasoning;
        private int menuCategoryWestern;
        private int menuCategoryEtc;
        private int menuDifficultyEasy;
        private int menuDifficultyMedium;
        private int menuDifficultyHard;
        private int menuCookTime0To1Hours;
        private int menuCookTime1HoursTo2Hours;
        private int menuCookTime2HoursTo4Hours;
        private int menuCookTime4HoursOver;
        private int menuCookCount;
    }
}
