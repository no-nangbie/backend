package com.nonangbie.memberFood.controller;

import com.nonangbie.auth.service.AuthService;
import com.nonangbie.dto.MultiResponseDto;
import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.food.entity.Food;
import com.nonangbie.member.entity.Member;
import com.nonangbie.memberFood.dto.MemberFoodDto;
import com.nonangbie.memberFood.entity.MemberFood;
import com.nonangbie.memberFood.mapper.MemberFoodMapper;
import com.nonangbie.memberFood.service.MemberFoodService;
import com.nonangbie.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/my-foods")
@Slf4j
@RequiredArgsConstructor
public class MemberFoodController {

    private final static String DEFAULT_MEMBER_FOOD_URL = "/my-foods";
    private final MemberFoodMapper memberFoodMapper;
    private final MemberFoodService memberFoodService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity postMemberFood(@Valid @RequestBody MemberFoodDto.Post requestBody,
                                         Authentication authentication) {
//        MemberFood memberFood = memberFoodMapper.memberFoodPostDtoToMemberFood(requestBody);
//        MemberFood createMemberFood = memberFoodService.createMemberFood(memberFood);
        MemberFood createMemberFood = memberFoodService.createMemberFood(memberFoodMapper.memberFoodPostDtoToMemberFood(requestBody),authentication);
        URI location = UriCreator.createUri(DEFAULT_MEMBER_FOOD_URL, createMemberFood.getMemberFoodId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{member-food-id}")
    public ResponseEntity patchMemberFood(@PathVariable("member-food-id") @Positive long memberFoodId,
                                          @Valid @RequestBody MemberFoodDto.Patch requestBody,
                                          Authentication authentication) {
        requestBody.setMemberFoodId(memberFoodId);
        MemberFood memberFood = memberFoodService.updateMemberFood(memberFoodMapper.memberFoodPatchDtoToMemberFood(requestBody),authentication);
        return new ResponseEntity(
                new SingleResponseDto<>(memberFoodMapper.memberFoodToMemberFoodResponseDto(memberFood)), HttpStatus.OK
        );
    }

    @GetMapping("/{member-food-id}")
    public ResponseEntity getMemberFood(@PathVariable("member-food-id") @Positive long memberFoodId,
                                        Authentication authentication) {
        MemberFood memberFood = memberFoodService.findMemberFood(memberFoodId,authentication);
        return new ResponseEntity<>(
                new SingleResponseDto<>(memberFoodMapper.memberFoodToMemberFoodResponseDto(memberFood)), HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getMemberFoods(@Positive @RequestParam int page,
                                         @Positive @RequestParam int size,
                                         @RequestParam String sort,
                                         Authentication authentication) {

        Sort sortOrder = Sort.by(sort.split("_")[0]).ascending();
        if(sort.split("_")[1].equalsIgnoreCase("desc")) {
            sortOrder = sortOrder.descending();
        }

        Page<MemberFood> pageMemberFood = memberFoodService.findMemberFoodsSort(page - 1, size, sortOrder, authentication);
        List<MemberFood> memberFoods = pageMemberFood.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(memberFoodMapper.memberFoodsToMemberFoodResponseDtos(memberFoods), pageMemberFood), HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity searchMemberFoods(@RequestParam String keyword,
                                            @Positive @RequestParam int page,
                                            @Positive @RequestParam int size,
                                            Authentication authentication)   {

        List<MemberFood> memberFoods = memberFoodService.searchMemberFoodsByKeyword(keyword,authentication);

        Page<MemberFood> memberFoodPage = new PageImpl<>(memberFoods, PageRequest.of(page, size), memberFoods.size());

        MultiResponseDto<MemberFoodDto.Response> responseDto = new MultiResponseDto<>(
                memberFoodMapper.memberFoodsToMemberFoodResponseDtos(memberFoods),
                memberFoodPage
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/search_by_category")
    public ResponseEntity searchByCategory(@RequestParam String keyword,
                                           @RequestParam String category,
                                           @Positive @RequestParam int page,
                                           @Positive @RequestParam int size,
                                           Authentication authentication)   {
        Food.FoodCategory foodCategory;
        try {
            foodCategory = Food.FoodCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity("Invalid food category", HttpStatus.BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("foodName").ascending());
        Page<MemberFood> memberFoods = memberFoodService.searchByCategory(pageable, keyword, foodCategory,authentication);
        MultiResponseDto<MemberFoodDto.Response> responseDto = new MultiResponseDto<>(
                memberFoodMapper.memberFoodsToMemberFoodResponseDtos(memberFoods.getContent()), memberFoods
        );

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{member-food-id}")
    public ResponseEntity deleteMemberFood(@PathVariable("member-food-id") @Positive long memberFoodId,Authentication authentication) {
        memberFoodService.deleteMemberFood(memberFoodId,authentication);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity deleteMultipleMemberFoods(@RequestBody List<Long> ids, Authentication authentication) {
        memberFoodService.deleteMultipleMemberFoods(ids, authentication);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
