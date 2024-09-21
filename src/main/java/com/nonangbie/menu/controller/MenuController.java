package com.nonangbie.menu.controller;

import com.nonangbie.auth.service.AuthService;
import com.nonangbie.board.entity.Board;
import com.nonangbie.dto.MultiResponseDto;
import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.menu.dto.MenuDto;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.menu.mapper.MenuMapper;
import com.nonangbie.menu.service.MenuService;
import com.nonangbie.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/menus")
@Slf4j
@RequiredArgsConstructor
public class MenuController {
    private final static String DEFAULT_MENU_URL = "/menus";
    private final MenuMapper menuMapper;
    private final MenuService menuService;
    private final AuthService authService;


    @PostMapping
    public ResponseEntity postMenu(@Valid @RequestBody MenuDto.Post menuPostDto,
                                   Authentication authentication) {
        Menu createdMenu = menuService.createMenu(menuMapper.menuPostDtoToMenu(menuPostDto),authentication);
        URI location = UriCreator.createUri(DEFAULT_MENU_URL, createdMenu.getMenuId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{menu-id}")
    public ResponseEntity patchMenu(@Valid @RequestBody MenuDto.Patch menuPatchDto,
                                    @PathVariable("menu-id") @Positive long menuId,
                                    Authentication authentication) {
        menuPatchDto.setMenuId(menuId);

        Menu patchMenu = menuService.updateMenu(menuMapper.menuPatchDtoToMenu(menuPatchDto),authentication);

        // 사용자의 보유 재료 목록을 가져옴
        List<String> memberFoodNames = menuService.getMemberFoodNames(authentication);

        return new ResponseEntity<>(new SingleResponseDto<>(menuMapper.menuToMenuResponseDto(patchMenu,false,memberFoodNames)), HttpStatus.OK);
    }

    /**
     * 새로운 메뉴 검색 메서드.
     * 기존 사용 하던 검색 메서드는 상황 별 마다 분리를 하였었는데, 부정확성과 잦은 오류로 인해 새롭게 만들었습니다.
     * 중복적인 코드의 사용을 방지하고 재사용성을 높이기 위해 하나로 통일 하였으며 동적 Query를 사용하여 유동적 검색이 가능해졌습니다.
     * @param page : 페이지
     * @param size : 불러올 사이즈
     * @param menuCategory : 메뉴의 카테고리
     * @param sort : 정렬 방식
     * @param keyword : 검색 키워드
     * @param foodName : 식재료 이름
     * @param authentication : jwt 토큰 인증정보
     * @return : 검색된 레시피 리스트
     * @author : 신민준
     */
    @GetMapping("/test")
    public ResponseEntity getMenus(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size,
                                   @RequestParam String menuCategory,
                                   @RequestParam String sort,
                                   @RequestParam String keyword,
                                   @RequestParam String foodName,
                                   Authentication authentication){

        //sort 날라오는 거 , menuLikeCount_asc , missingFoodsCount_asc, menuLikeCount_desc, likeList
        Page<Menu> pageMenu = menuService.findMenusIntegration(page - 1, size, menuCategory, sort, keyword, foodName,authentication);
        List<Menu> menus = pageMenu.getContent();

        // 사용자의 보유 재료 목록을 가져옴
        List<String> memberFoodNames = menuService.getMemberFoodNames(authentication);

        // 각 메뉴의 likeCheck 값을 가져옴
        List<Boolean> likeCheks = menus.stream()
                .map(menu -> menuService.findVerifiedMenuLike((String) authentication.getPrincipal(), menu))
                .collect(Collectors.toList());

        return new ResponseEntity(
                new MultiResponseDto<>(menuMapper.menusToMenuResponseDtos(menus, memberFoodNames, likeCheks), pageMenu), HttpStatus.OK
        );
    }

    @GetMapping("/recommendations")
    public ResponseEntity getRecommendMenus(@Positive @RequestParam int page,
                                            @Positive @RequestParam int size,
                                            @RequestParam String ateMenus,
                                            @RequestParam String menuCategories,
                                            Authentication authentication){
        Page<Menu> pageMenu = menuService.findMenuRecommendations(page-1,size,ateMenus,menuCategories,authentication);
    }

    @GetMapping("/{menu-id}")
    public ResponseEntity getMenu(@PathVariable("menu-id") @Positive long menuId,Authentication authentication) {
        Menu findMenu = menuService.findMenuById(menuId,authentication);


        // 현재 사용자의 보유 재료 리스트를 가져옴 (MemberFood 정보)
        List<String> memberFoodNames = menuService.getMemberFoodNames(authentication);
        boolean likeCheck = menuService.findVerifiedMenuLike((String) authentication.getPrincipal(),findMenu);

        return new ResponseEntity<>(new SingleResponseDto<>(menuMapper.menuToMenuResponseDto(findMenu,likeCheck,memberFoodNames)), HttpStatus.OK);
    }

    @DeleteMapping("/{menu-id}")
    public ResponseEntity deleteMenu(@PathVariable("menu-id") @Positive long menuId,Authentication authentication) {
        menuService.deleteMenu(menuId,authentication);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //메뉴 좋아요
    @PostMapping("/{menu-id}/like")
    public ResponseEntity postLike(@PathVariable("menu-id") @Positive long menuId,Authentication authentication) {
        return menuService.likeMenu(menuId,authentication) ?
                new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/recomend")
    public ResponseEntity recommendMenus(@PathVariable("menu-id") @Positive long menuId,Authentication authentication) {
        return menuService.likeMenu(menuId,authentication) ?
                new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
