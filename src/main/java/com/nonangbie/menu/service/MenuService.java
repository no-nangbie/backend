package com.nonangbie.menu.service;

import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.memberFood.entity.MemberFood;
import com.nonangbie.memberFood.repository.MemberFoodRepository;
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
    private final MemberFoodRepository memberFoodRepository;

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

    public List<String> getMemberFoodNames(Authentication authentication) {
        // 현재 인증된 사용자의 이메일을 이용하여 Member 객체를 가져옴

        Member member =  extractMemberFromAuthentication(authentication, memberRepository);

        // MemberFood 테이블에서 현재 사용자가 가지고 있는 재료 목록을 조회
        List<MemberFood> memberFoods = memberFoodRepository.findByMember(member);

        // 보유한 재료들의 이름을 List<String>으로 반환
        List<String> memberFoodNames = new ArrayList<>();
        for (MemberFood memberFood : memberFoods) {
            memberFoodNames.add(memberFood.getFood().getFoodName());
        }

        return memberFoodNames; // 보유 재료의 이름 리스트를 반환
    }

}
