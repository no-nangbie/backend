package com.nonangbie.AllergyFood.Mapper;

import com.nonangbie.AllergyFood.Dto.AllergyFoodDto;
import com.nonangbie.AllergyFood.Entity.AllergyFood;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AllergyFoodMapper {

    @Mapping(source = "foodName", target = "food.foodName")
    AllergyFood allergyFoodPostDtoToAllergyFood(AllergyFoodDto.Post requestBody);

    @Mapping(source = "foodName", target = "food.foodName")
    AllergyFood allergyFoodPatchDtoToAllergyFood(AllergyFoodDto.Patch requestBody);

    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "food.foodName", target = "foodName")
    AllergyFoodDto.Response allergyFoodToAllergyFoodResponseDto(AllergyFood allergyFood);

    List<AllergyFoodDto.Response> allergyFoodsToAllergyFoodResponseDtos(List<AllergyFood> allergyFoodList);


}
