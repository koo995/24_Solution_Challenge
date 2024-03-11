package com.gdsc.solutionchallenge.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class BooleanPredictedResult {

    @JsonProperty("living_things")
    private String livingThings;

    @JsonProperty("infer_result")
    private String inferResult;
}
