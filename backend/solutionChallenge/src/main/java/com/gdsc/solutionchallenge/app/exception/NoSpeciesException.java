package com.gdsc.solutionchallenge.app.exception;

import com.gdsc.solutionchallenge.exception.GeneralException;

public class NoSpeciesException extends GeneralException {

    private static final String MESSAGE = "찾으시는 종이 없습니다.";
    private static final int CODE = 404;
    public NoSpeciesException() {
        super(MESSAGE);
    }

    public NoSpeciesException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return CODE;
    }
}
