package com.gdsc.solutionchallenge.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PredictedResult {

    @JsonProperty("living_things")
    private String livingThings;

    @JsonProperty("scientific_name")
    private String scientificName;

    @JsonProperty("korea_name")
    private String koreaName;

    private String kingdom;
}
