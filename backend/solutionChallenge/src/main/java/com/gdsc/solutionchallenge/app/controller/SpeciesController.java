package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SpeciesController {

    private final SpeciesRepository speciesRepository;

    @GetMapping("/location/{speciesId}")
    public void getLocations(@PathVariable(name = "speciesId") Long speciesId) {
        speciesRepository.findById(speciesId);
    }
}
