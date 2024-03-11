package com.gdsc.solutionchallenge.app.service;

import com.gdsc.solutionchallenge.ai.dto.InferPredictedResult;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.dto.request.UserImageRequest;
import com.gdsc.solutionchallenge.app.dto.response.ImageCreateResponse;
import com.gdsc.solutionchallenge.app.dto.response.ImageDetailResponse;
import com.gdsc.solutionchallenge.app.exception.ImageNotFoundException;
import com.gdsc.solutionchallenge.app.exception.NoLatLngException;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.gdsc.solutionchallenge.file.FileStore;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.utils.ImgMetaDataExtractor;
import com.google.type.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageService {

    private final FileStore fileStore;
    private final ImageRepository imageRepository;
    private final SpeciesRepository speciesRepository;

    public ImageDetailResponse findImageById(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException(imageId));
        return new ImageDetailResponse(image);
    }

    @Transactional
    public ImageCreateResponse create(UserImageRequest userImageRequest, InferPredictedResult inferPredictedResult, Member loginMember){
        MultipartFile file = userImageRequest.getFile();
        // 메타데이터 추출.
        LatLng latLng;
        try {
            latLng = ImgMetaDataExtractor.extractLatLng(file);
        } catch (Exception e) {
            throw new NoLatLngException();
        }
        // 이미지 저장
        String fullPath = fileStore.storeFile(file);
        // 종을 가져옴 or 생성
        Species species = speciesRepository.findByScientificName(inferPredictedResult.getScientificName())
                .orElse(new Species(inferPredictedResult.getScientificName(), inferPredictedResult.getKoreaName(), inferPredictedResult.getKingdom()));
        Image image = Image.builder()
                .uploadFileName(file.getOriginalFilename())
                .fullPath(fullPath)
                .type(file.getContentType())
                .latLng(latLng)
                .build();
        int score = loginMember.addScore(10);
        image.setSpecies(species);
        image.setMember(loginMember);
        // 이미지포스트 저장
        speciesRepository.save(species);
        imageRepository.save(image).getId();
        return new ImageCreateResponse(inferPredictedResult.getScientificName(), inferPredictedResult.getKoreaName(), image.getId(), score);
    }
}
