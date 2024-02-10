package com.gdsc.solutionchallenge.app.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPostResponse {

    private Long postId;

    private String livingThings;

    private String scientificName;

    // todo 학명을 한국어로 디비에서 매칭시켜서 전달.

    public UserPostResponse(Long postId, String livingThings, String scientificName) {
        this.postId = postId;
        this.livingThings = livingThings;
        this.scientificName = scientificName;
    }
}
