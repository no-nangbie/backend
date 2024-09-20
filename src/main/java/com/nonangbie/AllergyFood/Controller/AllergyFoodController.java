package com.nonangbie.AllergyFood.Controller;

import com.nonangbie.AllergyFood.Dto.AllergyFoodDto;
import com.nonangbie.AllergyFood.Entity.AllergyFood;
import com.nonangbie.AllergyFood.Mapper.AllergyFoodMapper;
import com.nonangbie.AllergyFood.Service.AllergyFoodService;
import com.nonangbie.auth.service.AuthService;
import com.nonangbie.dto.MultiResponseDto;
import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
@RequestMapping("/allergy-foods")
@Slf4j
@RequiredArgsConstructor
public class AllergyFoodController {

    private final static String DEFAULT_ALLERGY_FOOD_URL = "/allergy-foods";
    private final AllergyFoodMapper allergyFoodMapper;
    private final AllergyFoodService allergyFoodService;
    private AuthService authService;

    @PostMapping
    public ResponseEntity postAllergyFood(@Valid @RequestBody AllergyFoodDto.Post requestBody,
                                          Authentication authentication) {
        AllergyFood createAllergyFood = allergyFoodService.createAllergyFood(allergyFoodMapper.allergyFoodPostDtoToAllergyFood(requestBody), authentication);
        URI location = UriCreator.createUri(DEFAULT_ALLERGY_FOOD_URL, createAllergyFood.getAllergyFoodId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{allergy-food-id}")
    public ResponseEntity patchAllergyFood(@PathVariable("allergy-food-id") @Positive long allergyFoodId,
                                           @Valid @RequestBody AllergyFoodDto.Patch requestBody,
                                           Authentication authentication) {
        requestBody.setAllergyFoodId(allergyFoodId);
        AllergyFood allergyFood = allergyFoodService.updateAllergyFood(allergyFoodMapper.allergyFoodPatchDtoToAllergyFood(requestBody), authentication);
        return new ResponseEntity<>(
                new SingleResponseDto<>(allergyFoodMapper.allergyFoodToAllergyFoodResponseDto(allergyFood)), HttpStatus.OK
        );
    }

    @GetMapping("/{allergy-food-id}")
    public ResponseEntity getAllergyFood(@PathVariable("allergy-food-id") @Positive long allergyFoodId,
                                         Authentication authentication) {
        AllergyFood allergyFood = allergyFoodService.findAllergyFood(allergyFoodId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(allergyFoodMapper.allergyFoodToAllergyFoodResponseDto(allergyFood)), HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getAllergyFood(@Positive @RequestParam int page,
                                         @Positive @RequestParam int size,
                                         Authentication authentication) {

        Page<AllergyFood> pageAllergyFood = allergyFoodService.findAllergyFoods(page - 1, size, authentication);
        List<AllergyFood> allergyFoods = pageAllergyFood.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(allergyFoodMapper.allergyFoodsToAllergyFoodResponseDtos(allergyFoods), pageAllergyFood), HttpStatus.OK
        );
    }

    @DeleteMapping("/{allergy-food-id}")
    public ResponseEntity deleteAllergyFood(@PathVariable("allergy-food-id") @Positive long allergyFoodId,
                                            Authentication authentication) {
        allergyFoodService.deleteAllergyFood(allergyFoodId, authentication);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity deleteMultipleAllergyFoods(@RequestBody List<Long> ids, Authentication authentication) {
        allergyFoodService.deleteMultipleAllergyFoods(ids, authentication);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
