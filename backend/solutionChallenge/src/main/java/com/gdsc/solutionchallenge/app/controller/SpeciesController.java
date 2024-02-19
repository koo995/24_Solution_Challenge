package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.app.dto.response.SpeciesImagesInfoDto;
import com.gdsc.solutionchallenge.app.service.SpeciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SpeciesController {

    private final SpeciesService speciesService;

    @GetMapping("/api/v1/{speciesId}/location")
    public SpeciesImagesInfoDto getLocations(@PathVariable(name = "speciesId") Long speciesId) {
        SpeciesImagesInfoDto oneOfLocations = speciesService.findOneOfLocations(speciesId);
        return oneOfLocations;
    }
}
