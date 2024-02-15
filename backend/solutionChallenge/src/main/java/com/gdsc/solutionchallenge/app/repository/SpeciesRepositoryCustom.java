package com.gdsc.solutionchallenge.app.repository;

import com.gdsc.solutionchallenge.app.dto.response.SpeciesImagesInfoDto;

public interface SpeciesRepositoryCustom {

    SpeciesImagesInfoDto findByIdWithImages(Long speciesId); // 반환타입을 뭘로할까? 전부다 가져오는 거니까? list?
}
