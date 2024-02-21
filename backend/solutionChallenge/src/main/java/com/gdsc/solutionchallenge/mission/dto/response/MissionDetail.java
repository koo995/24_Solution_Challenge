package com.gdsc.solutionchallenge.mission.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MissionDetail {

    private Long missionId;
    private String title;
    private String description;
    private String scientificName;
    private String koreaName;
    private Long speciesId;
    private String imageUrl;
    private String createdAt;
    private Boolean result;

    // todo 미션 성공여부를 담아줘야 겠는데? 사람마다 다름

    @Builder
    public MissionDetail(Long missionId, String title, String description, String scientificName, Long speciesId, String koreaName, String imageUrl, String createdAt, Boolean result) {
        this.missionId = missionId;
        this.title = title;
        this.description = description;
        this.scientificName = scientificName;
        this.koreaName = koreaName;
        this.speciesId = speciesId;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.result = result;
    }
}
