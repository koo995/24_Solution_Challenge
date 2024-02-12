package com.gdsc.solutionchallenge.app.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPostResponse {

    public static final String API_V_1_IMAGE = "/api/v1/image/";

    private Long imageId;

    private String scientificName;

    private String redirectUrl = API_V_1_IMAGE;

    // todo 학명을 한국어로 디비에서 매칭시켜서 전달.

    @Builder
    public UserPostResponse(Long imageId, String scientificName) {
        this.imageId = imageId;
        this.scientificName = scientificName;
        this.redirectUrl += imageId;
    }
}
