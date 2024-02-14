package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.ai.GeminiService;
import com.gdsc.solutionchallenge.ai.PredictedResult;
import com.gdsc.solutionchallenge.app.dto.request.UserImageRequest;
import com.gdsc.solutionchallenge.app.dto.response.ImageDetailResponse;
import com.gdsc.solutionchallenge.app.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;
    private final GeminiService geminiService;


    @PostMapping("/api/v1/image")
    public String create(@ModelAttribute UserImageRequest userImageRequest, RedirectAttributes redirectAttributes) {
        // gemini
        PredictedResult prediction = geminiService.prediction(userImageRequest.getFile());// todo living things가 아니라면 예외
        // 이미지포스트생성
        Long imageId = imageService.create(userImageRequest, prediction);
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
