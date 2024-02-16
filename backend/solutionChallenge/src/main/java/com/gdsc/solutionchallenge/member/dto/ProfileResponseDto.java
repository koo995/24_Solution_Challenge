package com.gdsc.solutionchallenge.member.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class ProfileResponseDto {

    // todo 기본 프로필 이미지가 필요했나?
    private String username;

    private Long totalImage;

    private Page<ImageWithFileDto> image;

    public ProfileResponseDto(String username, Long totalImage, Page<ImageWithFileDto> image) {
        this.username = username;
        this.totalImage = totalImage;
        this.image = image;
    }
}
