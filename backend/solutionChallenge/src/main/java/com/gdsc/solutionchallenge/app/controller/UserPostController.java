package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.ai.GeminiService;
import com.gdsc.solutionchallenge.ai.PostGeminiResult;
import com.gdsc.solutionchallenge.app.service.UserPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserPostController {

    private final UserPostService userPostService;
    private final GeminiService geminiService;

    @PostMapping("/user-post")
    public void createPost(@RequestParam(name = "image") MultipartFile request) throws IOException {
        PostGeminiResult postGeminiResult = geminiService.prediction(request);
        log.info("prediction={}", postGeminiResult);
    }
}
