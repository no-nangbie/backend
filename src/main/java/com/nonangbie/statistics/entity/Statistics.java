package com.nonangbie.statistics.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nonangbie.enumData.entity.MenuCookTime;
import com.nonangbie.enumData.entity.MenuDifficulty;
import com.nonangbie.member.entity.Member;
import com.nonangbie.enumData.entity.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statistics_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_category_id", nullable = true)
    @JsonBackReference
    private MenuCategory menuCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_cookTime_id", nullable = true)
    @JsonBackReference
    private MenuCookTime menuCookTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_difficulty_id", nullable = true)
    @JsonBackReference
    private MenuDifficulty menuDifficulty;

    @Column(name = "count", nullable = false)
    private int count = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "d_type", nullable = false)
    private DType infoType;

    @Getter
    @AllArgsConstructor
    public enum DType{
        CA("menuCategory"),
        CO("menuCookTime"),
        DI("menuDifficulty");

        private String status;

    }
}
