package com.nonangbie.foodMenu.mapper;

import com.nonangbie.foodMenu.dto.FoodMenuDto;
import com.nonangbie.foodMenu.entity.FoodMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface FoodMenuMapper {


    FoodMenu foodMenuPostDtoToFoodMenu(FoodMenuDto.Post requestBody);

    FoodMenu foodMenuPatchDtoToFoodMenu(FoodMenuDto.Patch requestBody);

    @Mapping(source = "food.foodId", target = "foodId")
    @Mapping(source = "menu.menuId", target = "menuId")
    FoodMenuDto.Response foodMenuToFoodMenuResponseDto(FoodMenu foodMenu);

    @Named("foodMenu")
    List<FoodMenuDto.Response> foodMenusToFoodMenuResponseDtos(List<FoodMenu> foodMenuList);
}
