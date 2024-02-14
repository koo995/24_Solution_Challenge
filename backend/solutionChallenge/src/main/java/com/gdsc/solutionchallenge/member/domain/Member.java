package com.gdsc.solutionchallenge.member.domain;


import com.gdsc.solutionchallenge.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = true) // todo 나중에 false로 바꾸기
    private String username;

    @Column(nullable = false)
    private String email;

    private int score;

    private String uid;


    @Builder
    public Member(String username, String email, int score, String uid) {
        this.username = username;
        this.email = email;
        this.score = score;
        this.uid = uid;
    }

    public int addScore(int score) {
        this.score += score;
        return this.score;
    }
}

