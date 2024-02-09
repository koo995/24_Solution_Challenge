package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.ai.GeminiService;
import com.gdsc.solutionchallenge.ai.PredictedSpecies;
import com.gdsc.solutionchallenge.app.dto.request.UserPostRequest;
import com.gdsc.solutionchallenge.app.service.UserPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserPostController {

    private final UserPostService userPostService;
    private final GeminiService geminiService;

    @PostMapping("/user-post")
    public PredictedSpecies createPost(@ModelAttribute UserPostRequest userPostRequest) {
        PredictedSpecies predictedSpecies = geminiService.prediction(userPostRequest.getImage()); //todo living things 가 아니면 예외 발생.
        userPostService.createPost(userPostRequest, predictedSpecies.getScientificName());
        return predictedSpecies;
    }
}
