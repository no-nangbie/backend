package com.nonangbie.food.controller;

import com.nonangbie.dto.MultiResponseDto;
import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.food.dto.FoodDto;
import com.nonangbie.food.entity.Food;
import com.nonangbie.food.mapper.FoodMapper;
import com.nonangbie.food.service.FoodService;
import com.nonangbie.utils.UriCreator;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/foods")
@Validated
@Slf4j
@RequiredArgsConstructor
public class FoodController {
    private final static String FOOD_DEFAULT_URL = "/foods";
    private final FoodService foodService;
    private final FoodMapper mapper;

    @PostMapping
    public ResponseEntity postFood(@Valid @RequestBody FoodDto.Post requestBody) {
        Food food = mapper.foodPostDtoToFood(requestBody);
        Food createFood = foodService.createFood(food);
        URI location = UriCreator.createUri(FOOD_DEFAULT_URL, createFood.getFoodId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{food-id}")
    public ResponseEntity patchFood(@PathVariable("food-id") @Positive long foodId,
                                    @Valid @RequestBody FoodDto.Patch requestBody) {
        requestBody.setFoodId(foodId);
        Food food = foodService.updateFood(mapper.foodPatchDtoToFood(requestBody));
        return new ResponseEntity(
                new SingleResponseDto<>(mapper.foodToFoodResponseDto(food)), HttpStatus.OK
        );
    }

    @GetMapping("/{food-id}")
    public ResponseEntity getFood(@PathVariable("food-id") @Positive long foodId) {
        Food food = foodService.findFood(foodId);
        return new ResponseEntity(
                new SingleResponseDto<>(mapper.foodToFoodResponseDto(food)), HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getFoods(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size,
                                   @RequestParam String sort,
                                   @RequestParam String category) {
        Sort sortOrder = Sort.by(sort.split("_")[0]).ascending();
        if (sort.split("_")[1].equalsIgnoreCase("desc")) {
            sortOrder = sortOrder.descending();
        }

        Food.FoodCategory foodCategory;
        try {
            foodCategory = Food.FoodCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity("유효하지 않은 카테고리입니다.", HttpStatus.BAD_REQUEST);
        }

        Page<Food> pageFood = foodService.findFoodsSort(page - 1, size, sortOrder, foodCategory);
        List<Food> foods = pageFood.getContent();
        return new ResponseEntity(
                new MultiResponseDto<>(mapper.foodsToFoodResponseDtos(foods), pageFood), HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam("keyword") String keyword,
                                 @PageableDefault(sort = "foodId", direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam("category") String category) {
        Food.FoodCategory foodCategory;
        try {
            foodCategory = Food.FoodCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity("Invalid category provided", HttpStatus.BAD_REQUEST);
        }

        int page = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());

        Page<Food> searchList = foodService.search(pageable, keyword, foodCategory);
        List<FoodDto.Response> responseList = searchList.stream()
                .map(mapper::foodToFoodResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity(
                new MultiResponseDto<>(responseList, searchList), HttpStatus.OK
        );
    }

    @DeleteMapping("/{food-id}")
    public ResponseEntity deleteFood(@PathVariable("food-id") @Positive long foodId) {
        foodService.deleteFood(foodId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
