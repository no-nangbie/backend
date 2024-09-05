package com.nonangbie.foodMenu.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nonangbie.food.entity.Food;
import com.nonangbie.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class FoodMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodMenuId;

    @Column(nullable = false)
    private String foodQuantity;

    @ManyToOne
    @JoinColumn(name = "FOOD_ID")
    @JsonBackReference
    private Food food;

    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    @JsonBackReference
    private Menu menu;
}
