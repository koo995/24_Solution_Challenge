package com.gdsc.solutionchallenge.ai.exception;

import com.gdsc.solutionchallenge.exception.GeneralException;

public class GeminiException extends GeneralException {

    private static final String MESSAGE = "gemini ai 실행도중 문제가 발생했습니다.";

    private static final int CODE = 400;


    public GeminiException() {
        super(MESSAGE);
    }

    public GeminiException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return CODE;
    }
}
