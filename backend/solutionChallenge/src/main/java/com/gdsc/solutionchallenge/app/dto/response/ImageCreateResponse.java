package com.gdsc.solutionchallenge.app.dto.response;

import lombok.Getter;

@Getter
public class ImageCreateResponse {

    private String scientificName;
    private Long imageId;

    public ImageCreateResponse(String scientificName, Long imageId) {
        this.scientificName = scientificName;
        this.imageId = imageId;
    }
}
