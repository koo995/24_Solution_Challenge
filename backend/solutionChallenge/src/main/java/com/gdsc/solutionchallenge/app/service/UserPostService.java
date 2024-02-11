package com.gdsc.solutionchallenge.app.service;

import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.domain.UserPost;
import com.gdsc.solutionchallenge.app.dto.request.UserPostRequest;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.gdsc.solutionchallenge.app.repository.UserPostRepository;
import com.gdsc.solutionchallenge.file.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class UserPostService {

    private final UserPostRepository userPostRepository;
    private final SpeciesRepository speciesRepository;
    private final ImageRepository imageRepository;
    private final FileStore fileStore;

    public Long createPost(UserPostRequest userPostRequest, String scientificName) throws IOException {
        MultipartFile file = userPostRequest.getFile();
        // 종을 가져옴 or 생성
        Species species = speciesRepository.findByName(scientificName)
                .orElse(new Species(scientificName));
        // 이미지 저장
        String storedFileName = fileStore.storeFile(file);
        // todo 이미지 파일에서 썸네일 만들기.
        Image image = Image.builder()
                .uploadFileName(file.getName())
                .storeFileName(storedFileName)
                .type(file.getContentType())
                .build();
        image.setSpecies(species);

        // 포스트 생성
        UserPost userPost = new UserPost();
        userPost.setImage(image);
        speciesRepository.save(species);
        imageRepository.save(image);
        UserPost savedPost = userPostRepository.save(userPost);
        return savedPost.getId();
    }
}
