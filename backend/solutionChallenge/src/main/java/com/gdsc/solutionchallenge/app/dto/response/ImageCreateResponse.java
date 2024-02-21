package com.gdsc.solutionchallenge.app.dto.response;

import lombok.Getter;

@Getter
public class ImageCreateResponse {

    private String scientificName;
    private String koreaName;
    private Long imageId;
    private int currentScore;

    public ImageCreateResponse(String scientificName, String koreaName, Long imageId, int currentScore) {
        this.scientificName = scientificName;
        this.koreaName = koreaName;
        this.imageId = imageId;
        this.currentScore = currentScore;
    }
}
