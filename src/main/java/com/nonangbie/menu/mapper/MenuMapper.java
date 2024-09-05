package com.nonangbie.menu.mapper;

import com.nonangbie.menu.dto.MenuDto;
import com.nonangbie.menu.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MenuMapper {
    default Menu menuPostDtoToMenu(MenuDto.Post menuPostDto){
        Menu menu = new Menu();

        menu.setMenuTitle(menuPostDto.getMenuTitle());
        menu.setMenuDescription(menuPostDto.getMenuDescription());
        menu.setMenuCategory(menuPostDto.getMenuCategory());
        menu.setImageUrl(menuPostDto.getImageUrl());
        menu.setCookingTime(menuPostDto.getCookingTime());
        menu.setServingSize(menuPostDto.getServingSize());
        menu.setDifficulty(menuPostDto.getDifficulty());


        List<String> recipeList = new ArrayList<>();
        for(String recipe : menuPostDto.getRecipes()){
            recipeList.add(recipe);
        }
        menu.setRecipes(recipeList);

        return menu;
    }
    Menu menuPatchDtoToMenu(MenuDto.Patch menuPatchDto);

    default MenuDto.Response menuToMenuResponseDto(Menu menu){
        MenuDto.Response menuResponseDto = new MenuDto.Response();

        menuResponseDto.setMenuId(menu.getMenuId());
        menuResponseDto.setMenuTitle(menu.getMenuTitle());
        menuResponseDto.setMenuDescription(menu.getMenuDescription());
        menuResponseDto.setMenuCategory(menu.getMenuCategory());
        menuResponseDto.setDifficulty(menu.getDifficulty());
        menuResponseDto.setCookingTime(menu.getCookingTime());
        menuResponseDto.setServingSize(menu.getServingSize());
        menuResponseDto.setRecipes(menu.getRecipes());
        menuResponseDto.setMenuLikeCount(menu.getMenuLikeCount());

        return menuResponseDto;
    }

    List<MenuDto.Response> menusToMenuResponseDtos(List<Menu> menus);
}
