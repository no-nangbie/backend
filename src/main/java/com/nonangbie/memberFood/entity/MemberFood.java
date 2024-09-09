package com.nonangbie.memberFood.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nonangbie.food.entity.Food;
import com.nonangbie.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class MemberFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberFoodId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Food.FoodCategory foodCategory;

    @Column(nullable = false)
    private String expirationDate;

    @Column(nullable = false)
    private String memo;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberFoodStatus memberFoodStatus;

    public enum MemberFoodStatus {
        Fresh("신선"),
        Approaching_Expiry("임박"),
        Near_Expiry("마감");

        @Getter
        private String memberFoodStatus;

        MemberFoodStatus(String memberFoodStatus) {
            this.memberFoodStatus = memberFoodStatus;
        }
    }

    @ManyToOne
    @JoinColumn(name = "FOOD_ID")
    @JsonBackReference
    private Food food;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    @JsonBackReference
    private Member member;
}
