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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

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
        Menu menu = menuService.updateMenu(menuMapper.menuPatchDtoToMenu(menuPatchDto));
        return new ResponseEntity<>(new SingleResponseDto<>(menuMapper.menuToMenuResponseDto(menu)), HttpStatus.OK);
    }

    @GetMapping("/{menu-id}")
    public ResponseEntity getMenu(@PathVariable("menu-id") @Positive long menuId) {
        Menu findMenu = menuService.findMenuById(menuId);
        return new ResponseEntity<>(new SingleResponseDto<>(menuMapper.menuToMenuResponseDto(findMenu)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMenus(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size) {

        Page<Menu> pageMenus = menuService.findMenus(page-1, size);
        List<Menu> findMenus = pageMenus.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(menuMapper.menusToMenuResponseDtos(findMenus),
                        pageMenus), HttpStatus.OK);
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
