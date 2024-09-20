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

    @GetMapping("/test")
    public ResponseEntity getMenus(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size,
                                   @RequestParam String menuCategory,
                                   @RequestParam String sort,
                                   @RequestParam String keyword,
                                   @RequestParam long foodId,
                                   Authentication authentication){

        //sort 날라오는 거 , menuLikeCount_asc , missingFoodsCount_asc, menuLikeCount_desc, likeList
        Page<Menu> pageMenu = menuService.findMenusIntegration(page - 1, size, menuCategory, sort, keyword, foodId,authentication);
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

    @GetMapping("all")
    public ResponseEntity getAllMenus(@Positive @RequestParam int page,
                                      @Positive @RequestParam int size,
                                      @RequestParam String sort,
                                      Authentication authentication) {
        // Sorting direction and property
        String[] sortParams = sort.split("_");
        String sortProperty = sortParams[0];
        String sortDirection = sortParams[1].toUpperCase();

        Sort sortOrder = Sort.by(Sort.Order.by(sortProperty).with(Sort.Direction.fromString(sortDirection)));

        Page<Menu> pageMenu = menuService.findMenusSort(page - 1, size, sortOrder, authentication);
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

    @GetMapping("/{menu-id}")
    public ResponseEntity getMenu(@PathVariable("menu-id") @Positive long menuId,Authentication authentication) {
        Menu findMenu = menuService.findMenuById(menuId,authentication);


        // 현재 사용자의 보유 재료 리스트를 가져옴 (MemberFood 정보)
        List<String> memberFoodNames = menuService.getMemberFoodNames(authentication);
        boolean likeCheck = menuService.findVerifiedMenuLike((String) authentication.getPrincipal(),findMenu);

        return new ResponseEntity<>(new SingleResponseDto<>(menuMapper.menuToMenuResponseDto(findMenu,likeCheck,memberFoodNames)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMenus(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size,
                                   @RequestParam String sort,
                                   @RequestParam String menuCategory,
                                   Authentication authentication) {
        Sort sortOrder = Sort.by(sort.split("_")[0]).ascending();
        if (sort.split("_")[1].equalsIgnoreCase("desc")) {
            sortOrder = sortOrder.descending();
        }

        Menu.MenuCategory menuCategory_;
        try {
            menuCategory_ = Menu.MenuCategory.valueOf(menuCategory);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity("유효하지 않은 카테고리입니다.", HttpStatus.BAD_REQUEST);
        }

        //추가
        // 현재 사용자의 보유 재료 리스트를 가져옴 (MemberFood 정보)
        List<String> memberFoodNames = menuService.getMemberFoodNames(authentication);

        Page<Menu> pageMenu = menuService.findMenuSort(page - 1, size, sortOrder, menuCategory_,authentication);
        List<Menu> menus = pageMenu.getContent();

        // 각 메뉴의 좋아요 여부를 확인하여 List<Boolean> 생성
        List<Boolean> likeChecks = menus.stream()
                .map(menu -> menuService.findVerifiedMenuLike((String) authentication.getPrincipal(), menu))
                .collect(Collectors.toList());

        return new ResponseEntity(
                new MultiResponseDto<>(menuMapper.menusToMenuResponseDtos(menus, memberFoodNames, likeChecks), pageMenu), HttpStatus.OK
        );
    }

    @GetMapping("search_by_category")
    public ResponseEntity search(@RequestParam("keyword") String keyword,
                                 @RequestParam(value = "sort", defaultValue = "menuId_desc") String sort, // 정렬 기준과 방향을 하나로 받음
                                 @PageableDefault Pageable pageable,
                                 @RequestParam("menuCategory") String menuCategory,
                                 Authentication authentication) {

        Menu.MenuCategory menuCategory_;
        try {
            menuCategory_ = Menu.MenuCategory.valueOf(menuCategory);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity("Invalid category provided", HttpStatus.BAD_REQUEST);
        }

        // 'sort' 파라미터를 '_asc' 또는 '_desc'로 분리하여 처리
        String[] sortParams = sort.split("_");
        if (sortParams.length != 2) {
            return new ResponseEntity("Invalid sort format. Expected format: 'field_direction'", HttpStatus.BAD_REQUEST);
        }

        String sortBy = sortParams[0]; // 정렬 기준 (menuId, likeCount 등)
        String sortDirectionStr = sortParams[1]; // 정렬 방향 (asc 또는 desc)

        // 정렬 방향을 Sort.Direction으로 변환
        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.valueOf(sortDirectionStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity("Invalid sort direction provided", HttpStatus.BAD_REQUEST);
        }

        // 동적으로 정렬 설정
        Sort sortOrder = Sort.by(sortBy).ascending(); // 기본적으로 ascending 설정
        if (sortDirection == Sort.Direction.DESC) {
            sortOrder = sortOrder.descending(); // desc일 경우 descending으로 변경
        }

        int page = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;

        // PageRequest에서 동적으로 정렬 기준 및 방향 설정
        pageable = PageRequest.of(page, pageable.getPageSize(), sortOrder);

        // 사용자의 보유 재료 목록을 가져옴
        List<String> memberFoodNames = menuService.getMemberFoodNames(authentication);

        // Menu 리스트를 검색한 후 보유 재료와 미보유 재료를 처리
        Page<Menu> searchList = menuService.search(pageable, keyword, menuCategory_, authentication);
        List<MenuDto.Response> responseList = searchList.getContent().stream()
                .map(menu -> menuMapper.menuToMenuResponseDto(menu, false, memberFoodNames)) // memberFoodNames 전달
                .collect(Collectors.toList());

        return new ResponseEntity(
                new MultiResponseDto<>(responseList, searchList), HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity searchMenu(@RequestParam String keyword,
                                     @Positive @RequestParam int page,
                                     @Positive @RequestParam int size,
                                     Authentication authentication) {

        List<Menu> menus = menuService.searchMenuByKeyword(keyword, authentication);
        Page<Menu> menuPage = new PageImpl<>(menus, PageRequest.of(page, size), menus.size());

        //추가
        // 사용자의 보유 재료 목록을 가져옴
        List<String> memberFoodNames = menuService.getMemberFoodNames(authentication);

        // 각 메뉴의 좋아요 여부를 확인하여 List<Boolean> 생성
        List<Boolean> likeChecks = menus.stream()
                .map(menu -> menuService.findVerifiedMenuLike((String) authentication.getPrincipal(), menu))
                .collect(Collectors.toList());

        MultiResponseDto<MenuDto.Response> responseDto = new MultiResponseDto<>(
                menuMapper.menusToMenuResponseDtos(menus, memberFoodNames, likeChecks), menuPage
        );
        return new ResponseEntity(responseDto, HttpStatus.OK);
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



}
