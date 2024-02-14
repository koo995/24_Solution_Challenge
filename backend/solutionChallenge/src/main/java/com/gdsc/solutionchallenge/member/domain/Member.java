package com.gdsc.solutionchallenge.member.domain;


import com.gdsc.solutionchallenge.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    private int score;

    // todo firebaseUid 추가해 줘야함.

    public int addScore(int score) {
        this.score += score;
        return this.score;
    }
}

