package com.gdsc.solutionchallenge.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ImageWithFileDto {

    private Long imageId;

    private String fullPath;

    @QueryProjection
    public ImageWithFileDto(Long imageId, String fullPath) {
        this.imageId = imageId;
        this.fullPath = fullPath;
    }
}
