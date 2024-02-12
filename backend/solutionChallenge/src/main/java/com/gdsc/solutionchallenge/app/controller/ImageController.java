package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.app.dto.response.ImageDetailResponse;
import com.gdsc.solutionchallenge.app.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/api/v1/image/{imageId}")
    public ImageDetailResponse detail(@PathVariable(name = "imageId") Long imageId) {
        ImageDetailResponse response = imageService.findImage(imageId);
        return response;
    }
}
