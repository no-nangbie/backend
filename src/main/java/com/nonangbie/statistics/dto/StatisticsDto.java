package com.nonangbie.statistics.dto;

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
}
