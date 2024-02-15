package com.gdsc.solutionchallenge.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileResponseDto {

    // todo 기본 프로필 이미지가 필요했나?
    private String username;

    private int totalImage;

    private List<ImageWithFileDto> image;

    @QueryProjection
    public ProfileResponseDto(String username, int totalImage, List<ImageWithFileDto> image) {
        this.username = username;
        this.totalImage = totalImage;
        this.image = image;
    }
}
