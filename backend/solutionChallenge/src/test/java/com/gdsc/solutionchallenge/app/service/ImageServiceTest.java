package com.gdsc.solutionchallenge.app.service;

import com.gdsc.solutionchallenge.IntegrationTestSupport;
import com.gdsc.solutionchallenge.ai.dto.InferPredictedResult;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.domain.converter.LatLngConverter;
import com.gdsc.solutionchallenge.app.dto.request.UserImageRequest;
import com.gdsc.solutionchallenge.app.dto.request.UserImageServiceRequest;
import com.gdsc.solutionchallenge.app.dto.response.ImageCreateResponse;
import com.gdsc.solutionchallenge.app.dto.response.ImageDetailResponse;
import com.gdsc.solutionchallenge.app.exception.ImageNotFoundException;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.gdsc.solutionchallenge.file.dto.FileStoreInfo;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.google.type.LatLng;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class ImageServiceTest extends IntegrationTestSupport {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    SpeciesRepository speciesRepository;

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
        assertThat(imageCreateResponse.getImageId()).isNotNull();
        assertThat(imageCreateResponse)
                .extracting("scientificName", "koreaName", "currentScore")
                .containsExactly("sciName", "korName", member.getScore());
    }

    @DisplayName("id을 이용하여 이미지객체를 찾는다.")
    @Test
    void findImageById() throws Exception {
        // given
        LatLng latLng = LatLng.newBuilder()
                .setLatitude(37.3)
                .setLongitude(127.5).build();
        Image image = Image.builder()
                .uploadFileName("uploadName")
                .fullPath("/*")
                .type("jpeg")
                .latLng(latLng)
                .build();

        Species species = new Species("sciName", "korName", "animal");
        image.setSpecies(species);
        speciesRepository.save((species));
        Image savedImage = imageRepository.save(image);

        // when
        ImageDetailResponse response = imageService.findImageById(savedImage.getId());
        System.out.println("response = " + response);

        // then
        assertThat(response.getImageId()).isNotNull();
        assertThat(response.getSpeciesId()).isNotNull();
        assertThat(response.getLocation()).isNotNull();
        assertThat(response).extracting("scientificName", "koreaName")
                .containsExactly("sciName", "korName");
    }

    @DisplayName("찾을려는 이미지객체가 없으면 예외가 발생한다.")
    @Test
    void findImageById2() throws Exception {
        Long imageId = 100l;
        assertThatThrownBy(() -> imageService.findImageById(imageId))
                .isInstanceOf(ImageNotFoundException.class);
    }
}