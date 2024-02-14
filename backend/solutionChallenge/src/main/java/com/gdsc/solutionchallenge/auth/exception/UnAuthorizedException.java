package com.gdsc.solutionchallenge.auth.exception;


import com.gdsc.solutionchallenge.exception.GeneralException;

public class UnAuthorizedException extends GeneralException {

    private static final String MESSAGE = "사용자 인증이 필요합니다.";
    private static final int statusCode = 401;


    public UnAuthorizedException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}
