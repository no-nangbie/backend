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
}
