package com.gdsc.solutionchallenge.app.service;

import com.gdsc.solutionchallenge.ai.dto.InferPredictedResult;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.dto.request.UserImageRequest;
import com.gdsc.solutionchallenge.app.dto.request.UserImageServiceRequest;
import com.gdsc.solutionchallenge.app.dto.response.ImageCreateResponse;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.gdsc.solutionchallenge.file.dto.FileStoreInfo;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.google.type.LatLng;
import com.google.type.LatLngOrBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ImageServiceTest {

    @Autowired
    SpeciesRepository speciesRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImageService imageService;

    @DisplayName("이미지 객체를 생성한다.")
    @Test
    void create() throws Exception {
        // given
        String fileName = "lion.jpeg";

        MultipartFile file = new MockMultipartFile("file", fileName, "image/jpeg", new FileInputStream("/Users/keonhongkoo/Downloads/"+fileName));
        InferPredictedResult prediction = InferPredictedResult.builder()
                .livingThings("true")
                .scientificName("sciName")
                .koreaName("korName")
                .build();
        FileStoreInfo fileStoreInfo= new FileStoreInfo(LatLng.newBuilder()
                .setLatitude(37.3)
                .setLongitude(127.5).build(), "/*");
        Member member = Member.builder()
                .uid("12345-asdf")
                .email("gunhong951@gmail.com")
                .username("koo")
                .build();
        UserImageRequest userImageRequest = new UserImageRequest();
        userImageRequest.setFile(file);
        UserImageServiceRequest serviceRequest = userImageRequest.toServiceRequest(prediction, fileStoreInfo, member);

        // when
        ImageCreateResponse imageCreateResponse = imageService.create(serviceRequest);

        // then
        assertThat(imageCreateResponse)
                .extracting("scientificName", "koreaName", "currentScore")
                .containsExactly("sciName", "korName", member.getScore());

    }

}