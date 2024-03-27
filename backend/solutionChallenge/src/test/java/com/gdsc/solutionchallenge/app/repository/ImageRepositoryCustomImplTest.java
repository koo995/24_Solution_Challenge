package com.gdsc.solutionchallenge.app.repository;

import com.gdsc.solutionchallenge.IntegrationTestSupport;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.member.dto.request.FilterCondition;
import com.gdsc.solutionchallenge.member.dto.response.ImageDto;
import com.gdsc.solutionchallenge.member.repository.MemberRepository;
import com.google.type.LatLng;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class ImageRepositoryCustomImplTest extends IntegrationTestSupport {

    @Autowired
    SpeciesRepository speciesRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("멤버 아이디를 통해 이미지를 조회한다.")
    @Test
    void findImageByMemberId() throws Exception {
        // given
        Member member = Member.builder()
                .uid("12345-asdf")
                .email("gunhong951@gmail.com")
                .username("koo")
                .build();
        ArrayList<Species> speciesList = new ArrayList<>();
        for (int i = 0; i < 20 ; i++ ) {
            Species species = Species.builder()
                    .kingdom("animal")
                    .koreaName("korName" + i)
                    .scientificName("sciName" + i)
                    .build();
            speciesList.add(species);
        }
        ArrayList<Image> imageList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Image image = Image.builder()
                    .fullPath("/*/" + i)
                    .uploadFileName("fileName" + i)
                    .latLng(LatLng.newBuilder()
                            .setLatitude(36.5)
                            .setLongitude(124.5).build())
                    .build();
            image.setSpecies(speciesList.get(i));
            image.setMember(member);
        }
        memberRepository.save(member);
        speciesRepository.saveAll(speciesList);
        imageRepository.saveAll(imageList);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        PageImpl<ImageDto> imageByMemberId = imageRepository.findImageByMemberId(member.getId(), new FilterCondition(), pageable);
        List<ImageDto> contents = imageByMemberId.getContent();

        // then
        assertThat(imageByMemberId.getTotalElements()).isEqualTo(20);
        assertThat(imageByMemberId.getSize()).isEqualTo(10);
        for (ImageDto content : contents) {
            assertThat(content.getImageId()).isNotNull();
            assertThat(content.getFullPath()).isNotNull();
        }
    }
}