package com.gdsc.solutionchallenge.app.exception;

import com.gdsc.solutionchallenge.exception.GeneralException;

public class ImagePostingException extends GeneralException {

    private static final String MESSAGE = "이미지 처리중 문제가 발생했습니다.";

    private static final int statusCode = 400;

    public ImagePostingException() {
        super(MESSAGE);
    }

    public ImagePostingException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
