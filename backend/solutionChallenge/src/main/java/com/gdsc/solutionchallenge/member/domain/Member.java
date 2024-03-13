package com.gdsc.solutionchallenge.member.domain;


import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.common.domain.BaseEntity;
import com.gdsc.solutionchallenge.mission.domain.MemberMission;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private int score = 0;

    private String uid;

    @OneToMany(mappedBy = "member")
    private List<Image> image = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberMission> memberMission = new ArrayList<>();


    @Builder
    public Member(String username, String email, String uid) {
        this.username = username;
        this.email = email;
        this.uid = uid;
    }

    public int addScore(int score) {
        this.score += score;
        return this.score;
    }

    public int getTotal() {
        return image.size();
    }

    // 연관관계 메서드
    public void addImage(Image image) {
        this.image.add(image);
    }

    public void addMemberMission(MemberMission memberMission) {
        this.memberMission.add(memberMission);
    }
}

