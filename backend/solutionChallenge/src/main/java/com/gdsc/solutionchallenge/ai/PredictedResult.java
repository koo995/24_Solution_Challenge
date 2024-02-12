package com.gdsc.solutionchallenge.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PredictedResult {

    @JsonProperty("living things")
    private String livingThings;

    @JsonProperty("scientific name")
    private String scientificName;
}
