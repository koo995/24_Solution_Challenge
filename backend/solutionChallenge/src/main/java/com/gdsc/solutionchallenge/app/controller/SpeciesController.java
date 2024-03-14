package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.app.dto.response.SpeciesImagesResponse;
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
    public SpeciesImagesResponse getLocations(@PathVariable(name = "speciesId") Long speciesId) {
        return speciesService.findOneOfLocations(speciesId);
    }
}
