package com.gdsc.solutionchallenge.app.repository;

import com.gdsc.solutionchallenge.app.dto.response.ImageInfoDto;
import com.gdsc.solutionchallenge.app.dto.response.SpeciesImagesInfoDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class SpeciesRepositoryCustomImpl implements SpeciesRepositoryCustom{

    private final EntityManager em;

    // todo 반드시 쿼리 한번 또는 dsl을 이용하여 최적화를 해보자.
    @Override
    public Optional<SpeciesImagesInfoDto> findByIdWithImages(Long speciesId) {

        List<ImageInfoDto> image = em.createQuery(
                        "select new com.gdsc.solutionchallenge.app.dto.response.ImageInfoDto(s.scientificName, i.id, i.createdDate, i.latLng)" +
                                " from Species s" +
                                " join s.image i" +
                                " where s.id = :speciesId", ImageInfoDto.class)
                .setParameter("speciesId", speciesId)
                .getResultList();

        return Optional.of(new SpeciesImagesInfoDto(speciesId, image.get(0).getScientificName(), image));
    }
}
