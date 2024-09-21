package com.nonangbie.menuRecommend.entity;

import com.nonangbie.member.entity.Member;
import com.nonangbie.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuRecommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuRecommendId;

    @Column(name = "score")
    private Integer score;

    @Column(name = "member_id")
    private Member member;

    @Column(name = "menu_id")
    private Menu menu;
}
