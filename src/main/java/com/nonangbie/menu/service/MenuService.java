package com.nonangbie.menu.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.menu.reposiitory.MenuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Menu menu) {
        Menu findMenu = findMenuById(menu.getMenuId());
        Optional.ofNullable(menu.getMenuTitle())
                .ifPresent(menuTitle -> findMenu.setMenuTitle(menu.getMenuTitle()));
        Optional.ofNullable(menu.getMenuDescription())
                .ifPresent(menuDescription -> findMenu.setMenuDescription(menu.getMenuDescription()));
        Optional.ofNullable(menu.getMenuCategory())
                .ifPresent(menuCategory -> findMenu.setMenuCategory(menu.getMenuCategory()));
        Optional.ofNullable(menu.getMenuCategory())
                .ifPresent(menuCategory -> findMenu.setMenuCategory(menu.getMenuCategory()));
        Optional.ofNullable(menu.getRecipes())
                .ifPresent(recipe -> findMenu.setRecipes(menu.getRecipes()));
        Optional.ofNullable(menu.getServingSize())
                .ifPresent(servingSize -> findMenu.setServingSize(menu.getServingSize()));
        Optional.ofNullable(menu.getImageUrl())
                .ifPresent(imageUrl -> findMenu.setImageUrl(menu.getImageUrl()));
        Optional.ofNullable(menu.getRecipes())
                .ifPresent(recipes -> findMenu.setRecipes(new ArrayList<>(recipes)));

        return menuRepository.save(findMenu);
    }

    public Menu findMenuById(long menuId){
        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        Menu findMenu = optionalMenu.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MENU_NOT_FOUND));

        return findMenu;
    }

    public Page<Menu> findMenus(int page, int size){
        return menuRepository.findAll(PageRequest.of(page, size, Sort.by("menuId")));
    }

    public Page<Menu> findMenuSort(int page, int size, Sort sort, Menu.MenuCategory menuCategory) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return menuRepository.findByMenuCategory(pageable, menuCategory);
    }

    public Page<Menu> search(Pageable pageable, String keyword, Menu.MenuCategory menuCategory) {
        Page<Menu> menuList = menuRepository.searchByMenuTitleAndCategory(pageable, keyword, menuCategory);
        return menuList;
    }

    public void deleteMenu(long menuId){
        Menu menu = findMenuById(menuId);
        menuRepository.delete(menu);
    }

    //메뉴 좋아요 기능(추후에 authentication으로 검증)
    public void createLike(long menuId){
        Menu menu = findMenuById(menuId);
        menu.setMenuLikeCount(menu.getMenuLikeCount() + 1);
        menuRepository.save(menu);
    }

}
