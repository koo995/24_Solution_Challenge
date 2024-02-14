package com.gdsc.solutionchallenge.ai.exception;

public class NoCreatureException extends GeminiException {

    private static final String MESSAGE = "사진속에 생명체가 없습니다.";

    public NoCreatureException() {
        super("image", MESSAGE);
    }
}
