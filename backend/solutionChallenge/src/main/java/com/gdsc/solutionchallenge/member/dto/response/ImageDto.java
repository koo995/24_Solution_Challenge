package com.gdsc.solutionchallenge.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ImageDto {

    private Long imageId;

    private String fullPath;

    @QueryProjection
    public ImageDto(Long imageId, String fullPath) {
        this.imageId = imageId;
        this.fullPath = fullPath;
    }
}
