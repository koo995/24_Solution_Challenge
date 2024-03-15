package com.gdsc.solutionchallenge.app.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SpeciesImagesResponse {

    private Long speciesId;

    private String scientificName;

    private String koreaName;

    private List<SpeciesImageQuery> images;

    @Builder
    public SpeciesImagesResponse(Long speciesId, String scientificName, String koreaName, List<SpeciesImageQuery> images) {
        this.speciesId = speciesId;
        this.scientificName = scientificName;
        this.koreaName = koreaName;
        this.images = images;
    }
}
