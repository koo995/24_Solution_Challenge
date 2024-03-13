package com.gdsc.solutionchallenge.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class InferPredictedResult {

    @JsonProperty("living_things")
    private String livingThings;

    @JsonProperty("scientific_name")
    private String scientificName;

    @JsonProperty("korea_name")
    private String koreaName;

    private String kingdom;

    @Builder
    public InferPredictedResult(String livingThings, String scientificName, String koreaName, String kingdom) {
        this.livingThings = livingThings;
        this.scientificName = scientificName;
        this.koreaName = koreaName;
        this.kingdom = kingdom;
    }
}
