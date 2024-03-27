package com.gdsc.solutionchallenge.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmptyProfileResponseDto extends ProfileResponseDto {

    private final Long totalImage = 0L;

    private final String sampleImage = "to do url";

    @Builder
    public EmptyProfileResponseDto(String username) {
        super(username, 0);
    }
}
