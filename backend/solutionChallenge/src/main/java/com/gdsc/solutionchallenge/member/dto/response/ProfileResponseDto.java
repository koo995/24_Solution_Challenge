package com.gdsc.solutionchallenge.member.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ProfileResponseDto {

    private String username;

    private int currentScore;
}
