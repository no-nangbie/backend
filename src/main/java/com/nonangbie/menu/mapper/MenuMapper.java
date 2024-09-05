package com.nonangbie.menu.mapper;

import com.nonangbie.menu.dto.MenuDto;
import com.nonangbie.menu.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

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

    MenuDto.Response menuToMenuResponseDto(Menu menu);

    List<MenuDto.Response> menusToMenuResponseDtos(List<Menu> menus);
}
