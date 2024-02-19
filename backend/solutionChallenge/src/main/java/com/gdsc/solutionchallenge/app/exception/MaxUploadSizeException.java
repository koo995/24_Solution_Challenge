package com.gdsc.solutionchallenge.app.exception;


import com.gdsc.solutionchallenge.exception.GeneralException;

public class MaxUploadSizeException extends GeneralException {

    private static final String MESSAGE = "이미지의 최대 크기 10MB을 초과하였습니다";
    private static final int CODE = 400;

    public MaxUploadSizeException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return CODE;
    }
}
