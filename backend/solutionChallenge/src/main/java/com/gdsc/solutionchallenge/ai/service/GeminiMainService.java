package com.gdsc.solutionchallenge.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.solutionchallenge.ai.dto.InferPredictedResult;
import com.gdsc.solutionchallenge.ai.dto.BooleanPredictedResult;
import com.gdsc.solutionchallenge.ai.exception.GeminiException;
import com.gdsc.solutionchallenge.ai.exception.NoCreatureException;
import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.Part;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import com.google.cloud.vertexai.generativeai.preview.PartMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GeminiMainService {

    private final GenerativeModel model;
    private final ObjectMapper objectMapper;

    public InferPredictedResult inferPrediction(MultipartFile file) {
        String prompt = "You are the best biologist in the world.\nFirst, check if there are any living things in the picture above.\nIf there is a living things, infer the exact scientific name of the creature.\nprovide the output in json format with \"living_things: true or false\", \"scientific_name\" , \"korea_name\" of scientific_name field.\nAnd add a field corresponding to \"\bkingdom\" and add it to the field \"kingdom\" whether the life equivalent to the scientific name is animal or plant.";
        try {
            String result = prediction(file, prompt);
            InferPredictedResult inferPredictedResult = objectMapper.readValue(result, InferPredictedResult.class);
            if (inferPredictedResult.getLivingThings().equals("false")) {
                throw new NoCreatureException();
            }
            return inferPredictedResult;
        } catch (Exception e) {
            GeminiException exception = new GeminiException("validation", "다른 이미지를 넣어주세요");
            exception.addValidation("image", e.getMessage());
            throw exception;
        }
    }



    public Boolean booleanPrediction(MultipartFile file, String scientificName) {
        String prompt = String.format("You are the best biologist in the world.\nPlease provide the output in json format with \"living_things: true or false\", \"infer_result: true or false\" .\nFirst, check if there are any living things in the picture above.\nIf there is a creature, the \"living_things\" field is true. If not, please provide false.\nAnd infer if the creature's exact scientific name is \"%s\".\nIf the inference result is correct, the field value of \"infer_result\" is true, otherwise, please provide false.", scientificName);
        String result = prediction(file, prompt);
        BooleanPredictedResult booleanPredictedResult = null;
        try {
            booleanPredictedResult = objectMapper.readValue(result, BooleanPredictedResult.class);
        } catch (JsonProcessingException e) {
            throw new GeminiException();
        }
        if (booleanPredictedResult.getLivingThings().equals("false")) {
            throw new NoCreatureException();
        }
        if (booleanPredictedResult.getInferResult().equals("false")) {
            return false;
        }
        return true;
    }

    private String prediction(MultipartFile file, String prompt) {
        List<Content> contents = createContents(file, prompt);
        try {
            GenerateContentResponse generateContentResponse = model.generateContent(contents);
            String result = generateContentResponse.getCandidates(0).getContent().getParts(0).getText().replace("```json", ""); //todo 여기에 하드코딩으로 인덱싱 해놓은것 뭔가 마음에 안든다.
            log.info("gemini={}", result);
            return result;
        } catch (Exception e) {
            throw new GeminiException("image", e.getMessage());
        }
    }

    private static List<Content> createContents(MultipartFile file, String prompt) {
        List<Content> contents = new ArrayList<>();
        try {
            contents.add(Content.newBuilder()
                    .setRole("user")
                    .addParts(PartMaker.fromMimeTypeAndData(file.getContentType(), file.getBytes())) // todo 유효한 이미지 타입이 이닌경우 처리해줘야한다
                    .addParts(Part.newBuilder().setText(prompt))
                    .build());
            return contents;
        } catch (Exception e) {
            throw new GeminiException("image", "파일을 읽을 수 없습니다.");
        }
    }
}
