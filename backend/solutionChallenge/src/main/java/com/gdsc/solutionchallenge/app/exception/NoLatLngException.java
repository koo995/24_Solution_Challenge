package com.gdsc.solutionchallenge.app.exception;

public class NoLatLngException extends ImagePostingException {

    private static final String MESSAGE = "사진의 메타정보에 위도와 경도가 존재하지 않습니다.";

    public NoLatLngException() {
        super("image", MESSAGE);
    }
}
