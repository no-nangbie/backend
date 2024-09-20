package com.nonangbie.AllergyFood.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nonangbie.food.entity.Food;
import com.nonangbie.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class AllergyFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long allergyFoodId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Food.FoodCategory foodCategory;

    @Column(nullable = false)
    private String foodName;

    @ManyToOne
    @JoinColumn(name = "FOOD_ID")
    @JsonBackReference
    private Food food;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    @JsonBackReference
    private Member member;
}
