package com.nonangbie.foodMenu.mapper;

import com.nonangbie.foodMenu.dto.FoodMenuDto;
import com.nonangbie.foodMenu.entity.FoodMenu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface FoodMenuMapper {

    FoodMenu foodMenuPostDtoToFoodMenu(FoodMenuDto.Post requestBody);

    FoodMenu foodMenuPatchDtoToFoodMenu(FoodMenuDto.Patch requestBody);

    FoodMenuDto.Response foodMenuToFoodMenuResponseDto(FoodMenu foodMenu);

    List<FoodMenuDto.Response> foodMenusToFoodMenuResponseDtos(List<FoodMenu> foodMenuList);
}
