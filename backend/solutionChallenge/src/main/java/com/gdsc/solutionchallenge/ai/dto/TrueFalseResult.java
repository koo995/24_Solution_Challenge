package com.gdsc.solutionchallenge.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrueFalseResult {

    @JsonProperty("living things")
    private String livingThings;

    @JsonProperty("infer_result")
    private String inferResult;
}
