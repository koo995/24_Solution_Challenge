package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.app.dto.response.SpeciesImagesInfoDto;
import com.gdsc.solutionchallenge.app.service.SpeciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/v1/")
public class SpeciesController {

    private final SpeciesService speciesService;

    @GetMapping("/location/{speciesId}")
    public SpeciesImagesInfoDto getLocations(@PathVariable(name = "speciesId") Long speciesId) {
        SpeciesImagesInfoDto oneOfLocations = speciesService.findOneOfLocations(speciesId);
        return oneOfLocations;
    }
}
