package com.gdsc.solutionchallenge.mission.dto.response;

import lombok.Getter;

@Getter
public class MissionListResponse {

    private Long missionId;
    private String title;
    private String missionComplete;

    public MissionListResponse(Long missionId, String title, String missionComplete) {
        this.missionId = missionId;
        this.title = title;
        this.missionComplete = missionComplete;
    }
}
