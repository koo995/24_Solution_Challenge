package com.gdsc.solutionchallenge.mission.dto.response;

import lombok.Getter;

@Getter
public class MissionResult {
    private String result = "미션에 성공하셨습니다.";
    private Long imageId;

    public MissionResult(Long imageId) {
        this.imageId = imageId;
    }
}
