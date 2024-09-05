package com.nonangbie.food.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    @Column(nullable = false)
    private String foodName;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private FoodCategory foodCategory;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private FoodStatus foodStatus = FoodStatus.FRESH;

    public enum FoodCategory {
        VEGETABLES_FRUITS("채소 및 과일류"),
        MEAT("육류"),
        FISH_SEAFOOD("어류 및 해산물"),
        EGGS_DAIRY("달걀 및 유제품"),
        SAUCES("소스류"),
        OTHERS("기타");

        @Getter
        private String foodCategory;

        FoodCategory(String foodCategory) {
            this.foodCategory = foodCategory;
        }
    }

    public enum FoodStatus {
        FRESH("여유"),
        APPROACHING_EXPIRY("임박"),
        EXPIRED("만료");

        @Getter
        private String foodStatus;

        FoodStatus(String foodStatus) {
            this.foodStatus = foodStatus;
        }

    }
}
