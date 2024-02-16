package com.gdsc.solutionchallenge.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.solutionchallenge.ai.dto.PredictedResult;
import com.gdsc.solutionchallenge.ai.dto.TrueFalseResult;
import com.gdsc.solutionchallenge.ai.exception.GeminiException;
import com.gdsc.solutionchallenge.ai.exception.NoCreatureException;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.*;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import com.google.cloud.vertexai.generativeai.preview.PartMaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class GeminiMainService {

    public PredictedResult prediction(MultipartFile file) {
        String inferSpeciesPrompt = "You are the best biologist in the world.\nFirst, check if there are any living things in the picture above.\nAnd infer the exact scientific name of the creature.\nPlease provide the output in json format with \"living things: true or false\", \"scientific name\" .";
        //todo 여기서 계속 모델을 생성해야 할까? 싱글톤으로 미리 만들어 놓으면 더 빠를 수 있지 않을까
        try (VertexAI vertexAi = new VertexAI("gdsc-seoultech", "asia-northeast3");) {
            GenerationConfig generationConfig =
                    GenerationConfig.newBuilder()
                            .setMaxOutputTokens(2048)
                            .setTemperature(0.1F)
                            .setTopK(15)
                            .setTopP(0.7F)
                            .build();
            GenerativeModel model = new GenerativeModel("gemini-pro-vision", generationConfig, vertexAi);
            List<SafetySetting> safetySettings = Arrays.asList(
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
            List<Content> contents = new ArrayList<>();
            contents.add(Content.newBuilder()
                    .setRole("user")
                    .addParts(PartMaker.fromMimeTypeAndData(file.getContentType(), file.getBytes())) // todo 유효한 이미지 타입이 이닌경우 처리해줘야한다
                    .addParts(Part.newBuilder().setText(inferSpeciesPrompt))
                    .build());
            GenerateContentResponse generateContentResponse = model.generateContent(contents, safetySettings);
            String text = generateContentResponse.getCandidates(0).getContent().getParts(0).getText().replace("```json", ""); //todo 여기에 하드코딩으로 인덱싱 해놓은것 뭔가 마음에 안든다.
            ObjectMapper objectMapper = new ObjectMapper(); // todo objectMapper()말고 resolver을 활용하면 어떨까
            PredictedResult predictedResult = objectMapper.readValue(text, PredictedResult.class);
            if (predictedResult.getLivingThings() == "false") {
                throw new NoCreatureException();
            }
            return predictedResult;
        } catch (Exception e) {
            throw new GeminiException("image", e.getMessage());
        }
    }

    public Boolean prediction2(MultipartFile file, String scientificName) {
        String trueOrFalsePrompt = String.format("You are the best biologist in the world.\nPlease provide the output in json format with \"living things: true or false\", \"infer result: true or false\" .\nFirst, check if there are any living things in the picture above.\nIf there is a creature, the \"living things\" field is true. If not, please provide false.\nAnd infer if the creature's exact scientific name is \"%s\".\nIf the inference result is correct, the field value of \"infer result\" is true, otherwise, please provide false.", scientificName);
        //todo 여기서 계속 모델을 생성해야 할까? 싱글톤으로 미리 만들어 놓으면 더 빠를 수 있지 않을까
        try (VertexAI vertexAi = new VertexAI("gdsc-seoultech", "asia-northeast3");) {
            GenerationConfig generationConfig =
                    GenerationConfig.newBuilder()
                            .setMaxOutputTokens(2048)
                            .setTemperature(0.1F)
                            .setTopK(15)
                            .setTopP(0.7F)
                            .build();
            GenerativeModel model = new GenerativeModel("gemini-pro-vision", generationConfig, vertexAi);
            List<SafetySetting> safetySettings = Arrays.asList(
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
            List<Content> contents = new ArrayList<>();
            contents.add(Content.newBuilder()
                    .setRole("user")
                    .addParts(PartMaker.fromMimeTypeAndData(file.getContentType(), file.getBytes())) // todo 유효한 이미지 타입이 이닌경우 처리해줘야한다
                    .addParts(Part.newBuilder().setText(trueOrFalsePrompt))
                    .build());
            GenerateContentResponse generateContentResponse = model.generateContent(contents, safetySettings);
            String text = generateContentResponse.getCandidates(0).getContent().getParts(0).getText().replace("```json", ""); //todo 여기에 하드코딩으로 인덱싱 해놓은것 뭔가 마음에 안든다.
            ObjectMapper objectMapper = new ObjectMapper(); // todo objectMapper()말고 resolver을 활용하면 어떨까
            TrueFalseResult trueFalseResult = objectMapper.readValue(text, TrueFalseResult.class);
            if (trueFalseResult.getLivingThings() == "false") {
                throw new NoCreatureException();
            }
            if (trueFalseResult.getInferResult() == "false") {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new GeminiException("image", e.getMessage());
        }
    }
}