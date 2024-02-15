package com.gdsc.solutionchallenge.app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SpeciesImagesInfoDto {

    @JsonProperty("species_id")
    private Long speciesId;

    @JsonProperty("scientific_name")
    private String scientificName;

    // todo 한글명 추가해주기

    private List<ImageInfoDto> images;

    @Builder
    public SpeciesImagesInfoDto(Long speciesId, String scientificName, List<ImageInfoDto> images) {
        this.speciesId = speciesId;
        this.scientificName = scientificName;
        this.images = images;
    }
}
