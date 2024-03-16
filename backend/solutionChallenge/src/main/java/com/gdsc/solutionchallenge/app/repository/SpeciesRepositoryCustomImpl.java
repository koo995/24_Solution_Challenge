package com.gdsc.solutionchallenge.app.repository;

import com.gdsc.solutionchallenge.app.dto.response.SpeciesImageQuery;
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

    @Override
    public SpeciesImagesResponse findByIdWithImages(Long speciesId) {

        List<SpeciesImageQuery> image = em.createQuery(
                        "select new com.gdsc.solutionchallenge.app.dto.response.SpeciesImageQuery(s.scientificName, s.koreaName, i.id, i.createdDate, i.latLng)" +
                                " from Species s" +
                                " join s.image i" +
                                " where s.id = :speciesId", SpeciesImageQuery.class)
                .setParameter("speciesId", speciesId)
                .getResultList();
        if (image.isEmpty()) {
            throw new NoSpeciesException();
        }
        // todo 이게 맞냐? 이런식으로 처리하는게... 과연 조인을 쓰는게 맞는걸까? 차라리 쿼리문 2개가 나을수도 있지 않을까?
        SpeciesImageQuery speciesImageQuery = image.get(0);
        return SpeciesImagesResponse.builder()
                .scientificName(speciesImageQuery.getScientificName())
                .koreaName(speciesImageQuery.getKoreaName())
                .speciesId(speciesId)
                .images(image).build();
    }
}
