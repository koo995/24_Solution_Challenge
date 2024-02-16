package com.gdsc.solutionchallenge.mission.domain;

import com.gdsc.solutionchallenge.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class MemberMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "mission_complete")
    private Boolean missionCompleteStatus = false;

    // 연관관계 메서드
    public void setMission(Mission mission) {
        this.mission = mission;
        mission.setMemberMission(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.addMemberMission(this);
    }

    // 생성 메서드
    public static MemberMission createMemberMission(Mission mission, Member member) {
        MemberMission memberMission = new MemberMission();
        memberMission.setMission(mission);
        memberMission.setMember(member);
        return memberMission;
    }

    // 비즈니스 로직
    public void missionComplete() {
        this.missionCompleteStatus = true;
    }
}
