package com.gdsc.solutionchallenge.app.service;

import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.dto.response.ImageDetailResponse;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageDetailResponse findImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException(" 찾으시는 이미지가 없어요"));
        return new ImageDetailResponse(image);
    }
}
