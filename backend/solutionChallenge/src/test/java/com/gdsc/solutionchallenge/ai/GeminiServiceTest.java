package com.gdsc.solutionchallenge.ai;

import com.gdsc.solutionchallenge.ai.dto.InferPredictedResult;
import com.gdsc.solutionchallenge.ai.service.GeminiMainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GeminiServiceTest {

    @Autowired
    private GeminiMainService geminiMainService;

    @DisplayName("이미지에서 생명체를 추론한다.")
    @Test
    void inferPrediction() throws Exception {
        // given
        String fileName = "lion.jpeg";
        MultipartFile file = new MockMultipartFile("file", fileName, "image/jpeg", new FileInputStream("/Users/keonhongkoo/Downloads/"+fileName));

        // when
        InferPredictedResult inferPredictedResult = geminiMainService.inferPrediction(file);

        // then
        assertThat(inferPredictedResult)
                .extracting("livingThings", "scientificName", "koreaName", "kingdom")
                .contains("true", "Panthera leo", "사자", "animal");
    }


    @DisplayName("주어진 종에 해당하는 이미지가 주어져야 한다..")
    @Test
    void booleanPrediction() throws Exception {
        // given
        String fileName1 = "lion.jpeg";
        String fileName2 = "cat.jpeg";
        String scientificName = "Panthera leo";
        MultipartFile file1 = new MockMultipartFile("file", fileName1, "image/jpeg", new FileInputStream("/Users/keonhongkoo/Downloads/"+fileName1));
        MultipartFile file2 = new MockMultipartFile("file", fileName2, "image/jpeg", new FileInputStream("/Users/keonhongkoo/Downloads/"+fileName2));

        // when
        Boolean result1 = geminiMainService.booleanPrediction(file1, scientificName);
        Boolean result2 = geminiMainService.booleanPrediction(file2, scientificName);

        // then
        assertTrue(result1 == Boolean.TRUE);
        assertTrue(result2 == Boolean.FALSE);

    }
}