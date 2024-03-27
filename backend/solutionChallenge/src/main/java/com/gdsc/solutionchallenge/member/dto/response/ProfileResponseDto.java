package com.gdsc.solutionchallenge.member.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class ProfileResponseDto {

    private final String username;

    private final int currentScore;

    public ProfileResponseDto(String username, int score) {
        this.username = username;
        this.currentScore = score;
    }
}
