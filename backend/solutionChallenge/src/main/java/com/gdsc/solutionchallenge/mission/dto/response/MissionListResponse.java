package com.gdsc.solutionchallenge.mission.dto.response;

import lombok.Getter;

@Getter
public class MissionListResponse {

    private Long missionId;
    private String missionComplete;

    public MissionListResponse(Long missionId, String missionComplete) {
        this.missionId = missionId;
        this.missionComplete = missionComplete;
    }
}
