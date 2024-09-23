package com.nonangbie.menu.service;

import com.nonangbie.AllergyFood.Entity.AllergyFood;
import com.nonangbie.AllergyFood.Repository.AllergyFoodRepository;
import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.food.entity.Food;
import com.nonangbie.food.repository.FoodRepository;
import com.nonangbie.foodMenu.entity.FoodMenu;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.memberFood.entity.MemberFood;
import com.nonangbie.memberFood.repository.MemberFoodRepository;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.menu.repository.MenuRepository;
import com.nonangbie.menuLike.entity.MenuLike;
import com.nonangbie.menuLike.repository.MenuLikeRepository;
//import com.nonangbie.menuRecommend.service.MenuRecommendService;
import com.nonangbie.utils.ExtractMemberEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nonangbie.menu.entity.Menu.MenuCategory.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService extends ExtractMemberEmail {

    private final FoodRepository foodRepository;
    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final MenuLikeRepository menuLikeRepository;
    private final AllergyFoodRepository allergyFoodRepository;
//    private final MenuRecommendService menuRecommendService;

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

    public static <T> Page<T> convertListToPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        // 리스트에서 해당 페이지 범위만큼 잘라서 Page 객체 생성
        List<T> subList = list.subList(start, end);
        return new PageImpl<>(subList, pageable, list.size());
    }

    public Page<Menu> findMenusIntegration(int page, int size, String menuCategory,
                                           String sort, String keyword, String foodName,
                                           Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
        Sort sortBy;
        Pageable pageable;
        Menu.MenuCategory mmc = null;
        Long foodId = -1L;
        //sort 날라오는 거 , menuLikeCount_asc , missingFoodsCount_asc, menuLikeCount_desc, likeList
        switch (sort) {
            case "likeList":
                pageable = PageRequest.of(page, size); break;
            case "menuLikeCount_asc":
                sortBy = Sort.by("menuLikeCount").ascending();
                pageable = PageRequest.of(page, size, sortBy); break;
            case "menuLikeCount_desc":
                sortBy = Sort.by("menuLikeCount").descending();
                pageable = PageRequest.of(page, size, sortBy); break;
            case "missingFoodsCount_asc":
                sortBy = Sort.by("missingFoodsCount").ascending();
                pageable = PageRequest.of(page, size, sortBy); break;
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sort);
        }
        if(menuCategory.isBlank() || menuCategory.isEmpty() ||
                menuCategory.equals("ALL")) {
            menuCategory = null;
        }
        else{
            mmc = findMenuCategory(menuCategory);
        }
        if(keyword.isBlank() || keyword.isEmpty())
            keyword = null;
        if(foodName.isBlank() || foodName.isEmpty() || foodName.equals("전체"))
            foodId = null;
        else {
            Food food = foodRepository.findByFoodName(foodName)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOOD_NOT_FOUND));
            foodId = food.getFoodId();
        }


        return menuRepository.findAllMenusIntegration(pageable,mmc,keyword,foodId);
    }
    //page-1,size,ateMenus,menuCategories,authentication
    public Page<Menu> findMenuRecommendations(int page,int size,String ateMenus,String menuCategories,Authentication authentication){
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        Pageable pageable = PageRequest.of(page, size);
        List<String> menuTitleList = new ArrayList<>();
        List<Menu.MenuCategory> menuCategoryList = new ArrayList<>();

        String[] categories = menuCategories.split("-");


        if(ateMenus.isEmpty() || ateMenus.isBlank()) {
            menuTitleList.clear();
        }else{
            menuTitleList = Arrays.asList(ateMenus.split("-"));
        }

        if(menuCategories.isEmpty() || menuCategories.isBlank()) {
            menuCategoryList.clear();
        }else {
            for (String category : categories) {
                Menu.MenuCategory foundCategory = findMenuCategoryUpperCase(category);
                menuCategoryList.add(foundCategory);
            }
        }
//        List<Menu> addMenuRecommend = menuRepository.findAllByRecommendations(menuTitleList,menuCategoryList);


        List<Menu> addMenuRecommend = menuRepository.findAllByRecommendations(menuCategoryList);

        List<String> memberFoodNames = getMemberFoodNames(authentication);

        List<Menu> RemoveMenuList = new ArrayList<>();

        List<AllergyFood> allergyFoodList = allergyFoodRepository.findByMember(member);

        List<String> allergyFoodList_ = new ArrayList<>();
        for(AllergyFood allergyFood : allergyFoodList){
            allergyFoodList_.add(allergyFood.getFoodName());
        }

        boolean loopTrigger = true;
        for(Menu menu : addMenuRecommend) {
            loopTrigger = true;
            for(String checkTitle : menuTitleList){
                if(menu.getMenuTitle().contains(checkTitle)){
                    loopTrigger = false;
                    break;
                }
            }
            if(loopTrigger) {
                for (FoodMenu foodMenu : menu.getFoodMenuList()) {
                    String foodName = foodMenu.getFood().getFoodName();
                    if (!memberFoodNames.contains(foodName) ||
                    allergyFoodList_.contains(foodName)) {
                        RemoveMenuList.add(menu);
                        break;
                    }
                }
            }
        }
        addMenuRecommend.removeAll(RemoveMenuList);
        // List를 Page로 변환
        Page<Menu> menuPage = convertListToPage(addMenuRecommend, pageable);

        return menuPage;
    }

    private Menu.MenuCategory findMenuCategory(String s){
        switch (s) {
            case "MENU_CATEGORY_SIDE":
                return MENU_CATEGORY_SIDE;
            case "MENU_CATEGORY_SOUP":
                return MENU_CATEGORY_SOUP;
            case "MENU_CATEGORY_DESSERT":
                return MENU_CATEGORY_DESSERT;
            case "MENU_CATEGORY_NOODLE":
                return MENU_CATEGORY_NOODLE;
            case "MENU_CATEGORY_RICE":
                return MENU_CATEGORY_RICE;
            case "MENU_CATEGORY_KIMCHI":
                return MENU_CATEGORY_KIMCHI;
            case "MENU_CATEGORY_FUSION":
                return MENU_CATEGORY_FUSION;
            case "MENU_CATEGORY_SEASONING":
                return MENU_CATEGORY_SEASONING;
            case "MENU_CATEGORY_WESTERN":
                return MENU_CATEGORY_WESTERN;
            default: //"기타":
                return MENU_CATEGORY_ETC;
        }
    }

    private Menu.MenuCategory findMenuCategoryUpperCase(String s){
        switch (s) {
            case "menuCategorySide":
                return MENU_CATEGORY_SIDE;
            case "menuCategorySoup":
                return MENU_CATEGORY_SOUP;
            case "menuCategoryDessert":
                return MENU_CATEGORY_DESSERT;
            case "menuCategoryNoodle":
                return MENU_CATEGORY_NOODLE;
            case "menuCategoryRice":
                return MENU_CATEGORY_RICE;
            case "menuCategoryKimchi":
                return MENU_CATEGORY_KIMCHI;
            case "menuCategoryFusion":
                return MENU_CATEGORY_FUSION;
            case "menuCategorySeasoning":
                return MENU_CATEGORY_SEASONING;
            case "menuCategoryWestern":
                return MENU_CATEGORY_WESTERN;
            default: //"기타":
                return MENU_CATEGORY_ETC;
        }
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
    public boolean likeMenu(long menuId, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication,memberRepository);
        Menu findMenu = findMenuById(menuId,authentication);
        if(findVerifiedMenuLike(member,findMenu)) {
            findMenu.setMenuLikeCount(findMenu.getMenuLikeCount() - 1);
            return false;
        }
        else {
            findMenu.setMenuLikeCount(findMenu.getMenuLikeCount() + 1);
            return true;
        }
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

    @Transactional(readOnly = true)
    public boolean findVerifiedMenuLike(String memberEmail, Menu menu) {
        Optional<MenuLike> findBoardLike = menuLikeRepository.findByMember_emailAndMenu(memberEmail,menu);
        return findBoardLike.isPresent();
    }

    private boolean findVerifiedMenuLike(Member member, Menu menu) {
        Optional<MenuLike> findMenuLike = menuLikeRepository.findByMemberAndMenu(member,menu);
        if(findMenuLike.isPresent()){
            menuLikeRepository.delete(findMenuLike.get());
            return true;
        }else{
            MenuLike menuLike = new MenuLike();
            menuLike.setMenu(menu);
            menuLike.setMember(member);
            menuLikeRepository.save(menuLike);
            return false;
        }
    }

}
