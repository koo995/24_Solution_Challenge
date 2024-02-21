package com.gdsc.solutionchallenge.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmptyProfileResponseDto extends ProfileResponseDto {

    private Long totalImage = 0l;

    private String sampleImage = "to do url";

}
