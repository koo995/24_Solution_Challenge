package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.ai.GeminiService;
import com.gdsc.solutionchallenge.ai.PredictedSpecies;
import com.gdsc.solutionchallenge.app.dto.request.UserPostRequest;
import com.gdsc.solutionchallenge.app.dto.response.UserPostResponse;
import com.gdsc.solutionchallenge.app.service.UserPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserPostController {

    private final UserPostService userPostService;
    private final GeminiService geminiService;

    @PostMapping("/user-post")
    public UserPostResponse createPost(@ModelAttribute UserPostRequest userPostRequest) throws IOException {
        // gemini
        PredictedSpecies predictedSpecies = geminiService.prediction(userPostRequest.getFile()); //todo living things 가 아니면 예외 발생.
        // 포스트 생성
        Long postId = userPostService.createPost(userPostRequest, predictedSpecies.getScientificName());
        return new UserPostResponse(postId, predictedSpecies.getLivingThings(), predictedSpecies.getScientificName()); // todo 이미지의 디테일 페이지로 리다이렉트 해주
    }
}
