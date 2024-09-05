package com.nonangbie.food.mapper;

import com.nonangbie.food.dto.FoodDto;
import com.nonangbie.food.entity.Food;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    Food foodPostDtoToFood(FoodDto.Post requestBody);

    Food foodPatchDtoToFood(FoodDto.Patch requestBody);

    FoodDto.Response foodToFoodResponseDto(Food food);

    List<FoodDto.Response> foodsToFoodResponseDtos(List<Food> foods);
}
