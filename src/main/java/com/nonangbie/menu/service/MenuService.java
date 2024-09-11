package com.nonangbie.menu.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.menu.reposiitory.MenuRepository;
import com.nonangbie.utils.ExtractMemberEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService extends ExtractMemberEmail {

    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;


    public Menu createMenu(Menu menu, Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Menu menu,Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Menu findMenu = findMenuById(menu.getMenuId(), authentication);
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

    public Menu findMenuById(long menuId,Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        Menu findMenu = optionalMenu.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MENU_NOT_FOUND));

        return findMenu;
    }

    public Page<Menu> findMenusSort(int page, int size, Sort sort, Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        return menuRepository.findAll(PageRequest.of(page, size, sort));
    }

    public Page<Menu> findMenuSort(int page, int size, Sort sort, Menu.MenuCategory menuCategory,Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Pageable pageable = PageRequest.of(page, size, sort);
        return menuRepository.findByMenuCategory(pageable, menuCategory);
    }

    public Page<Menu> search(Pageable pageable, String keyword, Menu.MenuCategory menuCategory,Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Page<Menu> menuList = menuRepository.searchByMenuTitleAndCategory(pageable, keyword, menuCategory);
        return menuList;
    }

    public List<Menu> searchMenuByKeyword(String keyword, Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
        return menuRepository.findByMenuTitleContainingOrMenuDescriptionContaining(keyword, keyword);
    }

    public void deleteMenu(long menuId,Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Menu menu = findMenuById(menuId,authentication);
        menuRepository.delete(menu);
    }

    //메뉴 좋아요 기능(추후에 authentication으로 검증)
    public void createLike(long menuId,Authentication authentication) {
        extractMemberFromAuthentication(authentication,memberRepository);
        Menu menu = findMenuById(menuId,authentication);
        menu.setMenuLikeCount(menu.getMenuLikeCount() + 1);
        menuRepository.save(menu);
    }

}
