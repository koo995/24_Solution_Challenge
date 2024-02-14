package com.gdsc.solutionchallenge.file.exception;

import com.gdsc.solutionchallenge.exception.GeneralException;

public class FileSaveException extends GeneralException {

    private static final String MESSAGE = "파일 저장중에 문제가 발생하였습니다.";

    private static final int statusCode = 500;

    public FileSaveException() {
        super(MESSAGE);
    }

    public FileSaveException(String field, String message) {
        super(MESSAGE);
        addValidation(field, message);
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
