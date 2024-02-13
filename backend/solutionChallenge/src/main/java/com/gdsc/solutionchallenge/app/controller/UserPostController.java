package com.gdsc.solutionchallenge.app.controller;

import com.gdsc.solutionchallenge.ai.GeminiService;
import com.gdsc.solutionchallenge.ai.PredictedResult;
import com.gdsc.solutionchallenge.app.dto.request.UserPostRequest;
import com.gdsc.solutionchallenge.app.dto.response.UserPostResponse;
import com.gdsc.solutionchallenge.app.service.UserPostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserPostController {

    private final UserPostService userPostService;
    private final GeminiService geminiService;

    @PostMapping(value = "/api/v1/user-post", consumes = {"multipart/form-data"} )
    public String createPost(@ModelAttribute UserPostRequest userPostRequest, RedirectAttributes redirectAttributes) throws IOException { // todo Maximum upload size
        // gemini
        PredictedResult predictedResult = geminiService.prediction(userPostRequest.getFile()); //todo living things 가 아니면 예외 발생.
        // 포스트 생성
        Long imageId = userPostService.createPost(userPostRequest, predictedResult);
        redirectAttributes.addAttribute("imageId", imageId);
        return "redirect:/api/v1/image/{imageId}";
    }
}
