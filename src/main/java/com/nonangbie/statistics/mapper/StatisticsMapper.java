package com.nonangbie.statistics.mapper;

import com.nonangbie.statistics.dto.StatisticsDto;
import com.nonangbie.statistics.entity.Statistics;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatisticsMapper {
    default StatisticsDto.FridgeResponse statisticsToStatisticsFridgeResponseDto(List<Statistics> statisticsList) {
        StatisticsDto.FridgeResponse response = new StatisticsDto.FridgeResponse();
        for(Statistics statistics : statisticsList){
            if(statistics.getInfoType().equals(Statistics.DType.COUNT)){
                switch (statistics.getDescription()){
                    case "FOOD_MANAGER_SCORE":
                        response.setFoodManagerScore(statistics.getCount()); break;
                    case "INPUT_FOOD_COUNT":
                        response.setInputFoodCount(statistics.getCount()); break;
                    case "OUTPUT_FOOD_COUNT":
                        response.setOutputFoodCount(statistics.getCount()); break;
                    case "EXPIRE_FOOD_COUNT":
                        response.setExpireFoodCount(statistics.getCount()); break;
                }
            }
        }
        return response;
    }

    default StatisticsDto.RecipeResponse statisticsToStatisticsRecipeResponseDto(List<Statistics> statisticsList) {
        StatisticsDto.RecipeResponse response = new StatisticsDto.RecipeResponse();
        for (Statistics statistics : statisticsList) {
                switch (statistics.getDescription()){
                    case "MENU_CATEGORY_SIDE":
                        response.setMenuCategorySide(statistics.getCount()); break;
                    case "MENU_CATEGORY_SOUP":
                        response.setMenuCategorySoup(statistics.getCount()); break;
                    case "MENU_CATEGORY_DESSERT":
                        response.setMenuCategoryDessert(statistics.getCount()); break;
                    case "MENU_CATEGORY_NOODLE":
                        response.setMenuCategoryNoodle(statistics.getCount()); break;
                    case "MENU_CATEGORY_RICE":
                        response.setMenuCategoryRice(statistics.getCount()); break;
                    case "MENU_CATEGORY_KIMCHI":
                        response.setMenuCategoryKimchi(statistics.getCount()); break;
                    case "MENU_CATEGORY_FUSION":
                        response.setMenuCategoryFusion(statistics.getCount()); break;
                    case "MENU_CATEGORY_SEASONING":
                        response.setMenuCategorySeasoning(statistics.getCount()); break;
                    case "MENU_CATEGORY_WESTERN":
                        response.setMenuCategoryWestern(statistics.getCount()); break;
                    case "MENU_CATEGORY_ETC":
                        response.setMenuCategoryEtc(statistics.getCount()); break;
                    case "DIFFICULTY_EASY":
                        response.setMenuDifficultyEasy(statistics.getCount()); break;
                    case "DIFFICULTY_MEDIUM":
                        response.setMenuDifficultyMedium(statistics.getCount()); break;
                    case "DIFFICULTY_HARD":
                        response.setMenuDifficultyHard(statistics.getCount()); break;
                    case "0_TO_1HOURS":
                        response.setMenuCookTime0To1Hours(statistics.getCount()); break;
                    case "1HOURS_TO_2HOURS":
                        response.setMenuCookTime1HoursTo2Hours(statistics.getCount()); break;
                    case "2HOURS_TO_4HOURS":
                        response.setMenuCookTime2HoursTo4Hours(statistics.getCount()); break;
                    case "4HOURS_OVER":
                        response.setMenuCookTime4HoursOver(statistics.getCount()); break;
                    case "COOK_COUNT":
                        response.setMenuCookCount(statistics.getCount()); break;
                }
        }
        return response;
    }

}
