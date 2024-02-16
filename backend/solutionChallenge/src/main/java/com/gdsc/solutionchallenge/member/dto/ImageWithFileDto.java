package com.gdsc.solutionchallenge.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ImageWithFileDto {

    private Long imageId;

    @JsonIgnore
    private String username;

    private String fullPath;

    @QueryProjection
    public ImageWithFileDto(Long imageId, String username, String fullPath) {
        this.imageId = imageId;
        this.username = username;
        this.fullPath = fullPath;
    }
}
