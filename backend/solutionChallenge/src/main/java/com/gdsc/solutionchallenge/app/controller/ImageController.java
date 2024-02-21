package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.ai.dto.PredictedResult;
import com.gdsc.solutionchallenge.ai.service.GeminiMainService;
import com.gdsc.solutionchallenge.app.dto.request.UserImageRequest;
import com.gdsc.solutionchallenge.app.dto.response.ImageCreateResponse;
import com.gdsc.solutionchallenge.app.dto.response.ImageDetailResponse;
import com.gdsc.solutionchallenge.app.service.ImageService;
import com.gdsc.solutionchallenge.auth.annotation.Login;
import com.gdsc.solutionchallenge.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;
    private final GeminiMainService geminiMainService;

    @PostMapping("/api/v1/image")
    public Long create(@ModelAttribute UserImageRequest userImageRequest, @Login Member LoginMember) {
        PredictedResult prediction = geminiMainService.prediction(userImageRequest.getFile());
        return imageService.create(userImageRequest, prediction, LoginMember);
    }

    @GetMapping("/api/v1/image/{imageId}")
    public ImageDetailResponse detail(@PathVariable(name = "imageId") Long imageId) {
        ImageDetailResponse response = imageService.findImageById(imageId);
        return response;
    }
}
