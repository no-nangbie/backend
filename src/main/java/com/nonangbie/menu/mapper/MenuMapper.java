package com.nonangbie.menu.mapper;

import com.nonangbie.foodMenu.dto.FoodMenuDto;
import com.nonangbie.foodMenu.entity.FoodMenu;
import com.nonangbie.foodMenu.mapper.FoodMenuMapper;
import com.nonangbie.menu.dto.MenuDto;
import com.nonangbie.menu.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {FoodMenuMapper.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MenuMapper {

    Menu menuPostDtoToMenu(MenuDto.Post menuPostDto);

    Menu menuPatchDtoToMenu(MenuDto.Patch menuPatchDto);


//    @Mapping(source = "foodMenuList",target = "foodMenuQuantityList", qualifiedByName = "foodMenusToFoodMenuResponse")
//    MenuDto.Response menuToMenuResponseDto(Menu menu);

    default MenuDto.Response menuToMenuResponseDto(Menu menu, Boolean likeCheck,List<String> memberFoodNames) {
        MenuDto.Response response = new MenuDto.Response();

        // Menu의 기본 필드 매핑
        response.setMenuId(menu.getMenuId());
        response.setMenuTitle(menu.getMenuTitle());
        response.setMenuDescription(menu.getMenuDescription());
        response.setMenuCategory(menu.getMenuCategory());
        response.setCookingTime(menu.getCookingTime());
        response.setServingSize(menu.getServingSize());
        response.setDifficulty(menu.getDifficulty());
        response.setRecipes(menu.getRecipes());
        response.setMenuLikeCount(menu.getMenuLikeCount());
        response.setCreatedAt(menu.getCreatedAt());
        response.setModifiedAt(menu.getModifiedAt());
        response.setImageUrl(menu.getImageUrl());
        if(likeCheck)
            response.setLikeCheck("T");

        // FoodMenu 리스트 변환
        List<FoodMenuDto.Response> foodMenuQuantityList = new ArrayList<>();
        List<String> ownedFoodsList = new ArrayList<>();
        List<String> missingFoodsList = new ArrayList<>();

        for (FoodMenu foodMenu : menu.getFoodMenuList()) {
            String foodName = foodMenu.getFood().getFoodName();
            foodMenuQuantityList.add(new FoodMenuDto.Response(foodName, foodMenu.getFoodQuantity()));

            // memberFoodNames에 포함된 경우 owned, 그렇지 않으면 missing
            if (memberFoodNames.contains(foodName)) {
                ownedFoodsList.add(foodName);
            } else {
                missingFoodsList.add(foodName);
            }
        }

        response.setFoodMenuQuantityList(foodMenuQuantityList);

        // 보유 재료와 미보유 재료를 콤마로 구분한 문자열로 변환하여 설정
        response.setOwnedFoods(String.join(", ", ownedFoodsList));
        response.setMissingFoods(String.join(", ", missingFoodsList));
        response.setMissingFoodsCount(missingFoodsList.size());

        return response;
    }
    // FoodMenu -> FoodMenuDto.Response 변환 메서드
    default FoodMenuDto.Response foodMenuToFoodMenuResponse(FoodMenu foodMenu) {
        return new FoodMenuDto.Response(
                foodMenu.getFood().getFoodName(),
                foodMenu.getFoodQuantity()
        );
    }


    // 보유 재료를 계산하는 메서드 - 콤마로 구분된 문자열 반환
    default String calculateOwnedFoods(Menu menu, List<String> memberFoodNames) {
        StringBuilder ownedFoodsBuilder = new StringBuilder();
        for (FoodMenu foodMenu : menu.getFoodMenuList()) {
            if (memberFoodNames.contains(foodMenu.getFood().getFoodName())) {
                if (ownedFoodsBuilder.length() > 0) {
                    ownedFoodsBuilder.append(", ");
                }
                ownedFoodsBuilder.append(foodMenu.getFood().getFoodName());
            }
        }
        return ownedFoodsBuilder.toString(); // 콤마로 구분된 문자열로 반환
    }
    // 미보유 재료를 계산하는 메서드 - 콤마로 구분된 문자열 반환
    default String calculateMissingFoods(Menu menu, List<String> memberFoodNames) {
        StringBuilder missingFoodsBuilder = new StringBuilder();
        for (FoodMenu foodMenu : menu.getFoodMenuList()) {
            if (!memberFoodNames.contains(foodMenu.getFood().getFoodName())) {
                if (missingFoodsBuilder.length() > 0) {
                    missingFoodsBuilder.append(", ");
                }
                missingFoodsBuilder.append(foodMenu.getFood().getFoodName());
            }
        }
        return missingFoodsBuilder.toString(); // 콤마로 구분된 문자열로 반환
    }

    // 여러 개의 메뉴에 대해 처리하는 메서드
    default List<MenuDto.Response> menusToMenuResponseDtos(List<Menu> menus, List<String> memberFoodNames, List<Boolean> likeChecks) {
        List<MenuDto.Response> responses = new ArrayList<>();

        // 메뉴와 likeCheck 값을 매칭하여 각각의 메뉴에 대한 응답을 생성
        for (int i = 0; i < menus.size(); i++) {
            Menu menu = menus.get(i);
            Boolean likeCheck = likeChecks.get(i); // 각 메뉴에 대해 likeCheck 값을 가져옴
            responses.add(menuToMenuResponseDto(menu, likeCheck, memberFoodNames));
        }

        return responses;
    }

    default List<MenuDto.ResponseRecommend> menusToMenuResponseRecommendDtos(List<Menu> menus){
        List<MenuDto.ResponseRecommend> responseRecommendList = new ArrayList<>();
        for (Menu menu : menus) {
            MenuDto.ResponseRecommend responseRecommend = new MenuDto.ResponseRecommend();
            responseRecommend.setMenuCategory(menu.getMenuCategory());
            responseRecommend.setMenuTitle(menu.getMenuTitle());
            responseRecommend.setMenuId(menu.getMenuId());
            responseRecommend.setMenuLikeCount(menu.getMenuLikeCount());
            responseRecommend.setImageUrl(menu.getImageUrl());
            responseRecommend.setMenuLikeCount(menu.getMenuLikeCount());
            responseRecommendList.add(responseRecommend);
        }
        return responseRecommendList;
    }

}
