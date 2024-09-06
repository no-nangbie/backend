package com.nonangbie.member.entity;

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

    @Column(name = "email", length = 20, nullable = false)
    private String email;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(name = "nickname", length = 20, nullable = false)
    private String nickname;

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
}

