package com.nonangbie.memberFood.mapper;

import com.nonangbie.memberFood.dto.MemberFoodDto;
import com.nonangbie.memberFood.entity.MemberFood;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MemberFoodMapper {

    @Mapping(source = "foodName", target = "food.foodName")
    MemberFood memberFoodPostDtoToMemberFood(MemberFoodDto.Post requestBody);

    @Mapping(source = "foodName", target = "food.foodName")
    MemberFood memberFoodPatchDtoToMemberFood(MemberFoodDto.Patch requestBody);

    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "food.foodName", target = "foodName")
    MemberFoodDto.Response memberFoodToMemberFoodResponseDto(MemberFood memberFood);

    List<MemberFoodDto.Response> memberFoodsToMemberFoodResponseDtos(List<MemberFood> memberFoods);
}
