package com.gdsc.solutionchallenge.mission.domain;

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

    @Column(name = "mission_complete")
    private Boolean missionCompleteStatus = false;

    // 연관관계 메서드
    public void setMission(Mission mission) {
        this.mission = mission;
        mission.setMemberMission(this);
    }

    // 생성 메서드
    public static MemberMission createMemberMission(Mission mission) {
        MemberMission memberMission = new MemberMission();
        memberMission.setMission(mission);
        return memberMission;
    }

    // 비즈니스 로직
    public void setMissionComplete() {
        this.missionCompleteStatus = true;
    }
}
