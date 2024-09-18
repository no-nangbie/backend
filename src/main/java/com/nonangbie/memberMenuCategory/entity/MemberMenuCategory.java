package com.nonangbie.memberMenuCategory.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nonangbie.member.entity.Member;
import com.nonangbie.menuCategory.entity.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "member_menu_category")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberMenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_menu_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_category_id", nullable = false)
    @JsonBackReference
    private MenuCategory menuCategory;

    @Column(name = "count", nullable = false)
    private int count;
}
