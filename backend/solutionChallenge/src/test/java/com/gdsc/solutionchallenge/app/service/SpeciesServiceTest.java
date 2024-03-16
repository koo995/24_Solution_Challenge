package com.gdsc.solutionchallenge.app.service;

import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.dto.response.SpeciesImagesResponse;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.google.type.LatLng;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class SpeciesServiceTest {

    @Autowired
    private SpeciesService speciesService;

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private ImageRepository imageRepository;

    @DisplayName("특정 종에 대한 이미지들을 가져온다")
    @Test
    void find() throws Exception {
        // given
        ArrayList<Species> speciesList = new ArrayList<>();
        for (int i = 0; i < 10 ; i++ ) {
            Species species = Species.builder()
                    .kingdom("animal")
                    .koreaName("korName" + i)
                    .scientificName("sciName" + i)
                    .build();
            speciesList.add(species);
        }
        Species species = speciesList.get(5);
        ArrayList<Image> imageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Image image = Image.builder()
                    .uploadFileName("fileName" + i)
                    .latLng(LatLng.newBuilder()
                            .setLatitude(36.5)
                            .setLongitude(124.5).build())
                    .build();
            image.setSpecies(species);
        }
        speciesRepository.saveAll(speciesList);
        imageRepository.saveAll(imageList);

        // when
        SpeciesImagesResponse response = speciesService.findOneOfLocations(species.getId());

        // then
        assertThat(response.getImages()).hasSize(10);
        assertThat(response)
                .extracting("scientificName", "koreaName")
                .containsExactly("sciName5", "korName5");
    }

}