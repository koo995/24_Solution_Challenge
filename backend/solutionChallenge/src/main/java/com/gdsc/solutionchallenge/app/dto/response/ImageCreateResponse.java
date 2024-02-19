package com.gdsc.solutionchallenge.app.dto.response;

import lombok.Getter;

@Getter
public class ImageCreateResponse {

    private String scientificName;
    private String koreaName;
    private Long imageId;

    public ImageCreateResponse(String scientificName, String koreaName, Long imageId) {
        this.scientificName = scientificName;
        this.koreaName = koreaName;
        this.imageId = imageId;
    }
}
