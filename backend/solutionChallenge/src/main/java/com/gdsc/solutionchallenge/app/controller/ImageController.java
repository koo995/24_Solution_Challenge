package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.ai.dto.InferPredictedResult;
import com.gdsc.solutionchallenge.ai.service.GeminiMainService;
import com.gdsc.solutionchallenge.app.dto.request.UserImageRequest;
import com.gdsc.solutionchallenge.app.dto.request.UserImageServiceRequest;
import com.gdsc.solutionchallenge.app.dto.response.ImageCreateResponse;
import com.gdsc.solutionchallenge.app.dto.response.ImageDetailResponse;
import com.gdsc.solutionchallenge.app.service.ImageService;
import com.gdsc.solutionchallenge.auth.annotation.Login;
import com.gdsc.solutionchallenge.file.FileStoreService;
import com.gdsc.solutionchallenge.file.dto.FileStoreInfo;
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
    private final FileStoreService fileStoreService;

    @PostMapping("/api/v1/image")
    public ImageCreateResponse create(@ModelAttribute UserImageRequest userImageRequest, @Login Member loginMember) {
        InferPredictedResult prediction = geminiMainService.inferPrediction(userImageRequest.getFile());
        FileStoreInfo fileStoreInfo = fileStoreService.extractFileInfoAndSave(userImageRequest.getFile());
        UserImageServiceRequest serviceRequest = userImageRequest.toServiceRequest(prediction, fileStoreInfo, loginMember);
        return imageService.create(serviceRequest);
    }


    @GetMapping("/api/v1/image/{imageId}")
    public ImageDetailResponse detail(@PathVariable(name = "imageId") Long imageId) {
        return imageService.findImageById(imageId);
    }
}
