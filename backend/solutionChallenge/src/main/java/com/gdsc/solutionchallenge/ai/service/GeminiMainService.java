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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GeminiMainService {

    private final GenerativeModel model;

    public PredictedResult prediction(MultipartFile file) {
        String inferSpeciesPrompt = "You are the best biologist in the world.\nFirst, check if there are any living things in the picture above.\nIf there is a living things, infer the exact scientific name of the creature.\nprovide the output in json format with \"living_things: true or false\", \"scientific_name\" , \"korea_name\" of scientific_name field.\nAnd add a field corresponding to \"\bkingdom\" and add it to the field \"kingdom\" whether the life equivalent to the scientific name is animal or plant.";
        try {
            List<Content> contents = getContents(file, inferSpeciesPrompt);
            GenerateContentResponse generateContentResponse = model.generateContent(contents);
            String text = generateContentResponse.getCandidates(0).getContent().getParts(0).getText().replace("```json", ""); //todo 여기에 하드코딩으로 인덱싱 해놓은것 뭔가 마음에 안든다.
            System.out.println("text = " + text);
            log.info("gemini={}", text);
            ObjectMapper objectMapper = new ObjectMapper(); // todo objectMapper()말고 resolver을 활용하면 어떨까
            PredictedResult predictedResult = objectMapper.readValue(text, PredictedResult.class);
            if (predictedResult.getLivingThings() == "false") {
                throw new NoCreatureException();
            }
            return predictedResult;
        } catch (Exception e) {
            GeminiException exception = new GeminiException("validation", "다른 이미지를 넣어주세요");
            exception.addValidation("image", e.getMessage());
            throw exception;
        }
    }


    public Boolean trueFalsePrediction(MultipartFile file, String scientificName) {
        String trueOrFalsePrompt = String.format("You are the best biologist in the world.\nPlease provide the output in json format with \"living_things: true or false\", \"infer_result: true or false\" .\nFirst, check if there are any living things in the picture above.\nIf there is a creature, the \"living_things\" field is true. If not, please provide false.\nAnd infer if the creature's exact scientific name is \"%s\".\nIf the inference result is correct, the field value of \"infer_result\" is true, otherwise, please provide false.", scientificName);
        try {
            List<Content> contents = getContents(file, trueOrFalsePrompt);
            GenerateContentResponse generateContentResponse = model.generateContent(contents);
            String text = generateContentResponse.getCandidates(0).getContent().getParts(0).getText().replace("```json", ""); //todo 여기에 하드코딩으로 인덱싱 해놓은것 뭔가 마음에 안든다.
            log.info("gemini={}", text);
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
            log.info("exception:",e);
            throw new GeminiException("image", e.getMessage());
        }
    }

    private static List<Content> getContents(MultipartFile file, String inferSpeciesPrompt) throws IOException {
        List<Content> contents = new ArrayList<>();
        contents.add(Content.newBuilder()
                .setRole("user")
                .addParts(PartMaker.fromMimeTypeAndData(file.getContentType(), file.getBytes())) // todo 유효한 이미지 타입이 이닌경우 처리해줘야한다
                .addParts(Part.newBuilder().setText(inferSpeciesPrompt))
                .build());
        return contents;
    }
}
