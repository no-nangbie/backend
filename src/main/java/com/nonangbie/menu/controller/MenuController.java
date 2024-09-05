package com.nonangbie.menu.controller;

import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.menu.dto.MenuDto;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.menu.mapper.MenuMapper;
import com.nonangbie.menu.reposiitory.MenuRepository;
import com.nonangbie.menu.service.MenuService;
import com.nonangbie.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@Validated
@RequestMapping("/menus")
@Slf4j
public class MenuController {
    private final static String DEFAULT_GUILD_URL = "/guilds";
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
        URI location = UriCreator.createUri(DEFAULT_GUILD_URL, createdMenu.getMenuId());

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

    @DeleteMapping("/{menu-id}")
    public ResponseEntity deleteMenu(@PathVariable("menu-id") @Positive long menuId) {
        menuService.deleteMenu(menuId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
