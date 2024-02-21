package com.gdsc.solutionchallenge.mission.dto.response;

import lombok.Getter;

@Getter
public class MissionCreateResponse {

    private Long missionId;

    public MissionCreateResponse(Long missionId) {
        this.missionId = missionId;
    }
}
