package com.gdsc.solutionchallenge.ai.config;

import com.gdsc.solutionchallenge.ai.exception.GeminiException;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.HarmCategory;
import com.google.cloud.vertexai.api.SafetySetting;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class GeminiConfig {

    @Bean
    public VertexAI vertexAI() {
        try {
            return new VertexAI("solchall-project", "asia-northeast3");
        } catch (IOException e) {
            throw new GeminiException();
        }
    }

    @Bean
    public GenerativeModel GenerativeModel(VertexAI vertexAi) {
        GenerationConfig generationConfig = getGenerationConfig();
        List<SafetySetting> safetySettings = getSafetySettings();
        GenerativeModel model = new GenerativeModel("gemini-pro-vision", generationConfig, vertexAi);
        model.setSafetySettings(safetySettings);
        return model;
    }

    private static GenerationConfig getGenerationConfig() {
        return GenerationConfig.newBuilder()
                .setMaxOutputTokens(2048)
                .setTemperature(0.1F)
                .setTopK(15)
                .setTopP(0.7F)
                .build();
    }

    private static List<SafetySetting> getSafetySettings() {
        return Arrays.asList(
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
                        .build()
        );
    }
}
