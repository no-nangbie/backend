package com.nonangbie.statistics.controller;

import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.statistics.entity.Statistics;
import com.nonangbie.statistics.mapper.StatisticsMapper;
import com.nonangbie.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
@Validated
public class StatisticsController {
    private final static String STATISTICS_DEFAULT_URL = "/statistics";
    private final StatisticsService service;
    private final StatisticsMapper mapper;

    @PatchMapping("/{menu-id}")
    public ResponseEntity patchStatistics(@PathVariable("menu-id") @Positive long menuId,
                                          Authentication authentication) {
        boolean result = service.increaseCookCount(menuId,authentication);
        if(result)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity getStatisticsFridge(@RequestParam String page,
                                              Authentication authentication){
        if(page.equals("fridge")) {
            List<Statistics> statisticsList = service.findVerifiedStatistics(page,authentication);
            return new ResponseEntity(
                    new SingleResponseDto<>(mapper.statisticsToStatisticsFridgeResponseDto(statisticsList)), HttpStatus.OK
            );
        }else if(page.equals("recipe")){
            List<Statistics> statisticsList = service.findVerifiedStatistics(page,authentication);
            return new ResponseEntity(
                    new SingleResponseDto<>(mapper.statisticsToStatisticsRecipeResponseDto(statisticsList)), HttpStatus.OK
            );
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity clearStatistics(Authentication authentication){
        service.clearStatistics(authentication);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
