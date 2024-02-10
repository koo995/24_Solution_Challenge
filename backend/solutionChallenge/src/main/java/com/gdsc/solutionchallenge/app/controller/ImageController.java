package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageRepository imageRepository;

    /**
     * 어떻게 하는 것일까 생각해 봤는데... 이미지가 저장된 장소에 대한 uri을 저장하고 그 녀석을 전달해 줘야하지
     */
    //todo 파일을 다시 보내줘야 함.
    @GetMapping("/api/v1/image/{imageId}")
    public void detail(@PathVariable(name = "imageId") Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("찾으시는 이미지가 없어요"));
    }
}
