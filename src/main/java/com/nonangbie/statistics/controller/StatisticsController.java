package com.nonangbie.statistics.controller;

import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.menu.dto.MenuDto;
import com.nonangbie.menu.entity.Menu;
import com.nonangbie.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping("/stats")
@Slf4j
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService service;


//    @PatchMapping("/{menu-category}")
//    public ResponseEntity patchMenu(@PathVariable("menu-category") @Positive String menuCategory,
//                                    Authentication authentication) {
//        service.updateMenu(menuMapper.menuPatchDtoToMenu(menuPatchDto),authentication);
//
//        // 사용자의 보유 재료 목록을 가져옴
//        List<String> memberFoodNames = menuService.getMemberFoodNames(authentication);
//
//        return new ResponseEntity<>(new SingleResponseDto<>(menuMapper.menuToMenuResponseDto(patchMenu,false,memberFoodNames)), HttpStatus.OK);
//    }
}
