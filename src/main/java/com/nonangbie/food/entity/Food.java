package com.nonangbie.food.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nonangbie.audit.Auditable;
import com.nonangbie.foodMenu.entity.FoodMenu;
import com.nonangbie.member.entity.Member;
import com.nonangbie.memberFood.entity.MemberFood;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Food extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    @Column(nullable = false)
    private String foodName;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private FoodCategory foodCategory;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

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

    @OneToMany(mappedBy = "food", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    @JsonManagedReference
    private List<FoodMenu> foodMenuList = new ArrayList<>();

    @OneToMany(mappedBy = "food", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    @JsonManagedReference
    private List<MemberFood> memberFoodList = new ArrayList<>();
}
