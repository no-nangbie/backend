package com.nonangbie.enumData.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nonangbie.statistics.entity.Statistics;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuCookTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cookTime_code", nullable = false, unique = true)
    private String code;

    @Column(name = "cookTime_name", nullable = false)
    private String name;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "menuCookTime")
//    private List<Statistics> statisticsList = new ArrayList<>();
}
