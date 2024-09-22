package com.nonangbie.member.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nonangbie.AllergyFood.Entity.AllergyFood;
import com.nonangbie.board.entity.Board;
import com.nonangbie.boardLike.entity.BoardLike;
import com.nonangbie.memberFood.entity.MemberFood;
import com.nonangbie.menuLike.entity.MenuLike;
import com.nonangbie.statistics.entity.Statistics;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "member")
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Setter
    @Column(name = "nickname", length = 255, nullable = false)
    private String nickname;

    @JsonManagedReference
    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    @JsonManagedReference
    private List<MemberFood> memberFoodList = new ArrayList<>();
//
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonManagedReference
    private List<BoardLike> boardLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonManagedReference
    private List<MenuLike> menuLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference
    private List<Statistics> statisticsList;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonManagedReference
    private List<AllergyFood> allergyFoodList = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private memberStatus status = memberStatus.MEMBER_ACTIVE;

    public Member(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public enum memberStatus {
        MEMBER_ACTIVE("회원 상태"),
        LOGGED_IN("로그인"),
        LOGGED_OUT("오프라인"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        memberStatus(String status) {
            this.status = status;
        }
    }

    public Member(String email) {
        this.email = email;
    }

}

