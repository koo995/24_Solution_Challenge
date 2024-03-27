package com.gdsc.solutionchallenge.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class MainProfileResponseDto extends ProfileResponseDto {

    private final Long totalImage;

    private final Page<ImageDto> image;

    @Builder
    public MainProfileResponseDto(String username, int score , Long totalImage, Page<ImageDto> image) {
        super(username, score);
        this.totalImage = totalImage;
        this.image = image;
    }
}
