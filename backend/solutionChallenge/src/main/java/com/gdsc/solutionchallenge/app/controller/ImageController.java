package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.ai.service.GeminiMainService;
import com.gdsc.solutionchallenge.ai.dto.PredictedResult;
import com.gdsc.solutionchallenge.app.dto.request.UserImageRequest;
import com.gdsc.solutionchallenge.app.dto.response.ImageDetailResponse;
import com.gdsc.solutionchallenge.app.service.ImageService;
import com.gdsc.solutionchallenge.auth.annotation.Login;
import com.gdsc.solutionchallenge.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;
    private final GeminiMainService geminiMainService;


    @PostMapping("/api/v1/image")
    public String create(@ModelAttribute UserImageRequest userImageRequest, RedirectAttributes redirectAttributes, @Login Member LoginMember) {
        // gemini
        PredictedResult prediction = geminiMainService.prediction(userImageRequest.getFile());
        // 이미지포스트생성
        Long imageId = imageService.create(userImageRequest, prediction, LoginMember);
        redirectAttributes.addAttribute("imageId", imageId);
        return "redirect:/api/v1/image/{imageId}";
    }

    @ResponseBody
    @GetMapping("/api/v1/image/{imageId}")
    public ImageDetailResponse detail(@PathVariable(name = "imageId") Long imageId) {
        ImageDetailResponse response = imageService.findImageById(imageId);
        return response;
    }
}
