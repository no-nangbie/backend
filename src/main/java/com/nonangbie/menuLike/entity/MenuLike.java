package com.nonangbie.menuLike.entity;

import com.nonangbie.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MenuLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
