package com.gdsc.solutionchallenge.app.service;

import com.gdsc.solutionchallenge.ai.PredictedResult;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.domain.UserPost;
import com.gdsc.solutionchallenge.app.dto.request.UserImageRequest;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.gdsc.solutionchallenge.app.repository.UserPostRepository;
import com.gdsc.solutionchallenge.file.FileStore;
import com.gdsc.solutionchallenge.utils.ImgMetaDataExtractor;
import com.google.type.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class UserPostService {

    private final UserPostRepository userPostRepository;
    private final SpeciesRepository speciesRepository;
    private final ImageRepository imageRepository;
    private final FileStore fileStore;

    public Long createPost(UserImageRequest userImageRequest, PredictedResult predictedResult) throws Exception {
        MultipartFile file = userImageRequest.getFile();
        // 메타데이터 추출.
        LatLng latLng = ImgMetaDataExtractor.extractLatLng(file);
        // 이미지 저장
        String fullPath = fileStore.storeFile(file);
        // 종을 가져옴 or 생성
        Species species = speciesRepository.findByScientificName(predictedResult.getScientificName())
                .orElse(new Species(predictedResult.getScientificName()));
        // todo 이미지 파일에서 썸네일 만들기.
        Image image = Image.builder()
                .uploadFileName(file.getOriginalFilename())
                .fullPath(fullPath)
                .type(file.getContentType())
                .latLng(latLng)
                .build();
        image.setSpecies(species);

        // 포스트 생성
        UserPost userPost = new UserPost();
        userPost.setImage(image);
        speciesRepository.save(species);
        Long imageId = imageRepository.save(image).getId();
        userPostRepository.save(userPost);
        return imageId;
    }
}
