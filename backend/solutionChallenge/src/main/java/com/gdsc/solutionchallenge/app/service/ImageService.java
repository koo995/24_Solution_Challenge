package com.gdsc.solutionchallenge.app.service;

import com.gdsc.solutionchallenge.ai.dto.InferPredictedResult;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.dto.request.UserImageServiceRequest;
import com.gdsc.solutionchallenge.app.dto.response.ImageCreateResponse;
import com.gdsc.solutionchallenge.app.dto.response.ImageDetailResponse;
import com.gdsc.solutionchallenge.app.exception.ImageNotFoundException;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.gdsc.solutionchallenge.file.dto.FileStoreInfo;
import com.gdsc.solutionchallenge.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final SpeciesRepository speciesRepository;
    private final int CREATESCORE = 10;

    public ImageDetailResponse findImageById(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException(imageId));
        return new ImageDetailResponse(image);
    }

    @Transactional
    public ImageCreateResponse create(UserImageServiceRequest request){
        Species species = getSpecies(request);
        Image image = getImage(request);
        Member loginMember = request.getLoginMember();
        int score = loginMember.addScore(CREATESCORE);
        image.setSpecies(species);
        image.setMember(loginMember);
        // 이미지포스트 저장
        speciesRepository.save(species);
        imageRepository.save(image);
        return new ImageCreateResponse(species.getScientificName(), species.getKoreaName(), image.getId(), score);
    }

    private Image getImage(UserImageServiceRequest request) {
        FileStoreInfo fileStoreInfo = request.getFileStoreInfo();
        Image image = Image.builder()
                .uploadFileName(request.getFile().getOriginalFilename())
                .fullPath(fileStoreInfo.getFullPath())
                .type(request.getFile().getContentType())
                .latLng(fileStoreInfo.getLatLng())
                .build();
        return image;
    }

    // todo 여기에서도 요청이 동시에 온다면... 같은 객체가 2개 생성될 수 있다. 하지만 위에 하나의 트랜잭션 안에 속하기 때문에 괜찮을까?
    private Species getSpecies(UserImageServiceRequest request) {
        InferPredictedResult prediction = request.getPrediction();
        Species species = speciesRepository.findByScientificName(prediction.getScientificName())
                .orElse(new Species(prediction.getScientificName(), prediction.getKoreaName(), prediction.getKingdom()));
        return species;
    }
}
