package com.gdsc.solutionchallenge.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.solutionchallenge.ai.dto.InferPredictedResult;
import com.gdsc.solutionchallenge.ai.service.GeminiMainService;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeminiServiceTest {

    @Autowired
    private ResourceLoader loader;

    @Autowired
    private GenerativeModel model;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GeminiMainService geminiMainService;

    @DisplayName("이미지에서 생명체를 추론한다.")
    @Test
    void inferPrediction() throws Exception {
        // given
        String fileName = "lion.jpeg";
        MultipartFile file = new MockMultipartFile("file", fileName, "image/jpeg", new FileInputStream("/Users/keonhongkoo/Downloads/lion.jpeg"));

        // when
        InferPredictedResult inferPredictedResult = geminiMainService.inferPrediction(file);

        // then
        assertThat(inferPredictedResult)
                .extracting("livingThings", "scientificName", "koreaName", "kingdom")
                .contains("true", "Panthera leo", "사자", "animal");
    }


}