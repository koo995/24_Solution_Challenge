package com.gdsc.solutionchallenge.app.service;

import com.gdsc.solutionchallenge.ai.PredictedResult;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.dto.request.UserImageRequest;
import com.gdsc.solutionchallenge.app.dto.response.ImageDetailResponse;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.gdsc.solutionchallenge.file.FileStore;
import com.gdsc.solutionchallenge.utils.ImgMetaDataExtractor;
import com.google.type.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final FileStore fileStore;
    private final ImageRepository imageRepository;
    private final SpeciesRepository speciesRepository;

    public ImageDetailResponse findImageById(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException(" 찾으시는 이미지가 없어요"));
        return new ImageDetailResponse(image);
    }

    public Long create(UserImageRequest userImageRequest, PredictedResult predictedResult) throws Exception {
        MultipartFile file = userImageRequest.getFile();
        // 메타데이터 추출.
        LatLng latLng = ImgMetaDataExtractor.extractLatLng(file);
        // 이미지 저장
        String fullPath = fileStore.storeFile(file);
        // 종을 가져옴 or 생성
        Species species = speciesRepository.findByScientificName(predictedResult.getScientificName())
                .orElse(new Species(predictedResult.getScientificName()));
        Image image = Image.builder()
                .uploadFileName(file.getOriginalFilename())
                .fullPath(fullPath)
                .type(file.getContentType())
                .latLng(latLng)
                .build();
        image.setSpecies(species);
        // 이미지포스트 생성
        speciesRepository.save(species);
        Long imageId = imageRepository.save(image).getId();
        return imageId;
    }
}
