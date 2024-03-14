package com.gdsc.solutionchallenge.app.repository;

import com.gdsc.solutionchallenge.app.dto.response.ImageInfoDto;
import com.gdsc.solutionchallenge.app.dto.response.SpeciesImagesResponse;
import com.gdsc.solutionchallenge.app.exception.NoSpeciesException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SpeciesRepositoryCustomImpl implements SpeciesRepositoryCustom{

    private final EntityManager em;

    // todo 반드시 쿼리 한번 또는 dsl을 이용하여 최적화를 해보자.
    @Override
    public SpeciesImagesResponse findByIdWithImages(Long speciesId) {

        List<ImageInfoDto> image = em.createQuery(
                        "select new com.gdsc.solutionchallenge.app.dto.response.ImageInfoDto(s.scientificName, s.koreaName, i.id, i.createdDate, i.latLng)" +
                                " from Species s" +
                                " join s.image i" +
                                " where s.id = :speciesId", ImageInfoDto.class)
                .setParameter("speciesId", speciesId)
                .getResultList();
        if (image.isEmpty()) {
            throw new NoSpeciesException();
        }
        ImageInfoDto imageInfoDto = image.get(0);


        return SpeciesImagesResponse.builder()
                .scientificName(imageInfoDto.getScientificName())
                .koreaName(imageInfoDto.getKoreaName())
                .speciesId(speciesId)
                .images(image).build();
    }
}
