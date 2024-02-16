package com.gdsc.solutionchallenge.mission.exception;

import com.gdsc.solutionchallenge.exception.GeneralException;

public class AlreadyExistSpeciesMissionException extends GeneralException {

    private static final String MESSAGE = "이미 미션이 존재하는 종입니다.";
    private static final int CODE = 400;

    public AlreadyExistSpeciesMissionException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return CODE;
    }
}
