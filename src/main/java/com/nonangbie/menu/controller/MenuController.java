package com.nonangbie.menu.controller;

import com.nonangbie.dto.MultiResponseDto;
import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.menu.dto.MenuDto;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.menu.mapper.MenuMapper;
import com.nonangbie.menu.reposiitory.MenuRepository;
import com.nonangbie.menu.service.MenuService;
import com.nonangbie.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class MenuController {
    private final static String DEFAULT_MENU_URL = "/menus";
    private final MenuMapper menuMapper;
    private final MenuService menuService;

    public MenuController(MenuMapper menuMapper, MenuService menuService) {
        this.menuMapper = menuMapper;
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity postMenu(@Valid @RequestBody MenuDto.Post menuPostDto) {
        Menu menu = menuMapper.menuPostDtoToMenu(menuPostDto);
        Menu createdMenu = menuService.createMenu(menu);
        URI location = UriCreator.createUri(DEFAULT_MENU_URL, createdMenu.getMenuId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{menu-id}")
    public ResponseEntity patchMenu(@Valid @RequestBody MenuDto.Patch menuPatchDto,
                                    @PathVariable("menu-id") @Positive long menuId) {
        menuPatchDto.setMenuId(menuId);

        Menu patchMenu = menuMapper.menuPatchDtoToMenu(menuPatchDto);

        Menu menu = menuService.updateMenu(patchMenu);

        return new ResponseEntity<>(new SingleResponseDto<>(menuMapper.menuToMenuResponseDto(menu)), HttpStatus.OK);
    }

    @GetMapping("/{menu-id}")
    public ResponseEntity getMenu(@PathVariable("menu-id") @Positive long menuId) {
        Menu findMenu = menuService.findMenuById(menuId);
        return new ResponseEntity<>(new SingleResponseDto<>(menuMapper.menuToMenuResponseDto(findMenu)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMenus(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size,
                                   @RequestParam String sort,
                                   @RequestParam String menuCategory) {
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

        Page<Menu> pageMenu = menuService.findMenuSort(page - 1, size, sortOrder, menuCategory_);
        List<Menu> menus = pageMenu.getContent();
        return new ResponseEntity(
                new MultiResponseDto<>(menuMapper.menusToMenuResponseDtos(menus), pageMenu), HttpStatus.OK
        );
    }

    @GetMapping("search")
    public ResponseEntity search(@RequestParam("keyword") String keyword,
                                 @RequestParam(value = "sort", defaultValue = "menuId_desc") String sort, // 정렬 기준과 방향을 하나로 받음
                                 @PageableDefault Pageable pageable,
                                 @RequestParam("menuCategory") String menuCategory) {

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

        Page<Menu> searchList = menuService.search(pageable, keyword, menuCategory_);
        List<MenuDto.Response> responseList = searchList.stream()
                .map(menuMapper::menuToMenuResponseDto)
                .collect(Collectors.toList());

        return new ResponseEntity(
                new MultiResponseDto<>(responseList, searchList), HttpStatus.OK
        );
    }

    @DeleteMapping("/{menu-id}")
    public ResponseEntity deleteMenu(@PathVariable("menu-id") @Positive long menuId) {
        menuService.deleteMenu(menuId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //메뉴 좋아요
    @PostMapping("/{menu-id}/like")
    public ResponseEntity postLike(@PathVariable("menu-id") @Positive long menuId) {
        menuService.createLike(menuId);
        return new ResponseEntity(HttpStatus.OK);
    }



}
