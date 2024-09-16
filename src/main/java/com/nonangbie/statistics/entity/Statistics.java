package com.nonangbie.statistics.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nonangbie.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long statisticsId;

    @Column(name = "cook_count", nullable = false)
    private int cookCount = 0;

    @Column(name = "side", nullable = false)
    private int side = 0;

    @Column(name = "soup", nullable = false)
    private int soup = 0;

    @Column(name = "dessert", nullable = false)
    private int dessert = 0;

    @Column(name = "noodle", nullable = false)
    private int noodle = 0;

    @Column(name = "rice", nullable = false)
    private int rice = 0;

    @Column(name = "kimchi", nullable = false)
    private int kimchi = 0;

    @Column(name = "fusion", nullable = false)
    private int fusion = 0;

    @Column(name = "seasoning", nullable = false)
    private int seasoning = 0;

    @Column(name = "western", nullable = false)
    private int western = 0;

    @Column(name = "etc", nullable = false)
    private int etc = 0;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    @JsonBackReference
    private Member member;

}
