package com.gdsc.solutionchallenge.mission.exception;

import com.gdsc.solutionchallenge.exception.GeneralException;

public class MissionFailException extends GeneralException {

    private static final String MESSAGE = "미션에 실패하였습니다.";
    private static final int CODE = 400;

    public MissionFailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return CODE;
    }
}
