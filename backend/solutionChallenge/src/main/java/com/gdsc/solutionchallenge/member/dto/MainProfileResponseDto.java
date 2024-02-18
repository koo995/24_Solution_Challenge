package com.gdsc.solutionchallenge.member.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class MainProfileResponseDto extends ProfileResponseDto{

    private Long totalImage;

    private Page<ImageDto> image;

    public MainProfileResponseDto(Long totalImage, Page<ImageDto> image) {
        this.totalImage = totalImage;
        this.image = image;
    }
}
