//package com.nonangbie.foodMenu.controller;
//
//import com.nonangbie.dto.MultiResponseDto;
//import com.nonangbie.foodMenu.entity.FoodMenu;
//import com.nonangbie.foodMenu.mapper.FoodMenuMapper;
//import com.nonangbie.foodMenu.service.FoodMenuService;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.constraints.Positive;
//import java.util.List;
//
//@RestController
//@RequestMapping("/menus/{menu-id}/foodmenus")
//public class FoodMenuController {
//    private final FoodMenuService foodMenuService;
//    private final FoodMenuMapper mapper;
//
//    public FoodMenuController(FoodMenuService foodMenuService, FoodMenuMapper mapper) {
//        this.foodMenuService = foodMenuService;
//        this.mapper = mapper;
//    }
//
//
//    @GetMapping
//    public ResponseEntity getFoodMenus(@Positive @RequestParam int page,
//                                       @Positive @RequestParam int size) {
//
//        Page<FoodMenu> pageFoodMenus = foodMenuService.findFoodMenus(page - 1, size);
//        List<FoodMenu> foodMenus = pageFoodMenus.getContent();
//        return new ResponseEntity(
//                new MultiResponseDto<>(mapper.foodMenusToFoodMenuResponseDtos(foodMenus), pageFoodMenus), HttpStatus.OK
//        );
//
//    }
//}
