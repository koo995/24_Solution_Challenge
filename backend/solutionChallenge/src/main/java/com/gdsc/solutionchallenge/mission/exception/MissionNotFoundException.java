package com.gdsc.solutionchallenge.mission.exception;

import com.gdsc.solutionchallenge.exception.GeneralException;

public class MissionNotFoundException extends GeneralException {

    private static final String MESSAGE = "찾으시는 미션이 없습니다.";
    private static final int CODE = 404;

    public MissionNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return CODE;
    }
}
