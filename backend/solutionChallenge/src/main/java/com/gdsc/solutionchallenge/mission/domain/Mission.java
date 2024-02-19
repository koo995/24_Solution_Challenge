package com.gdsc.solutionchallenge.mission.domain;

import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.common.domain.BaseEntity;
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
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    private String title;

    private String description;

    private String imageUrl;

    private int participantCount;

    // 연관관계의 주인으로 설정
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id")
    private Species species;

    @OneToMany(mappedBy = "mission",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberMission> memberMission = new ArrayList<>();

    // 연관관계 메서드
    public void setSpecies(Species species) {
        this.species = species;
        species.setMission(this);
    }

    public void setMemberMission(MemberMission memberMission) {
        this.memberMission.add(memberMission);
    }

    // 생성
    @Builder
    public Mission(String title, String description, String imageUrl, Species species) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.setSpecies(species);
    }
}
