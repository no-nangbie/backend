package com.nonangbie.statistics.controller;

import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.statistics.service.StatisticsService;
import com.nonangbie.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
@Validated
public class StatisticsController {
    private final static String STATISTICS_DEFAULT_URL = "/statistics";
    private final StatisticsService service;

    @PatchMapping("/{menu-id}")
    public ResponseEntity patchStatistics(@PathVariable("menu-id") @Positive long menuId,
                                          Authentication authentication) {
        boolean result = service.incrementCount(menuId,authentication);
        if(result)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
