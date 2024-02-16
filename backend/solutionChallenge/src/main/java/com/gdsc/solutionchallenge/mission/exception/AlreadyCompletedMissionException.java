package com.gdsc.solutionchallenge.mission.exception;

import com.gdsc.solutionchallenge.exception.GeneralException;

public class AlreadyCompletedMissionException extends GeneralException {

    private static final String MESSAGE = "해당 미션은 이미 완료하셨습니다.";

    private static final int CODE = 400;

    public AlreadyCompletedMissionException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return CODE;
    }
}
