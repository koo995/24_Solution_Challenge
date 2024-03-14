package com.gdsc.solutionchallenge.app.service;

import com.gdsc.solutionchallenge.app.dto.response.SpeciesImagesResponse;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class SpeciesService {

    private final SpeciesRepository speciesRepository;

    public SpeciesImagesResponse findOneOfLocations(Long speciesId) {
        return speciesRepository.findByIdWithImages(speciesId);
    }
}
