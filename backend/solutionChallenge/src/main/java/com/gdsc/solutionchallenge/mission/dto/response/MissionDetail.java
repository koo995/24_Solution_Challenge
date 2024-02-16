package com.gdsc.solutionchallenge.mission.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MissionDetail {

    private Long missionId;
    private String title;
    private String description;
    private String speciesName;
    private String imageUrl;
    private String createdAt;

    @Builder
    public MissionDetail(Long missionId, String title, String description, String speciesName, String imageUrl, String createdAt) {
        this.missionId = missionId;
        this.title = title;
        this.description = description;
        this.speciesName = speciesName;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }
}
