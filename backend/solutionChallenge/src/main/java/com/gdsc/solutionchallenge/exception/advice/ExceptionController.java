package com.gdsc.solutionchallenge.exception.advice;

import com.gdsc.solutionchallenge.ai.exception.GeminiException;
import com.gdsc.solutionchallenge.ai.exception.NoCreatureException;
import com.gdsc.solutionchallenge.app.exception.ImageNotFoundException;
import com.gdsc.solutionchallenge.app.exception.NoLatLngException;
import com.gdsc.solutionchallenge.app.exception.NoSpeciesException;
import com.gdsc.solutionchallenge.auth.exception.UnAuthorizedException;
import com.gdsc.solutionchallenge.exception.dto.ErrorResponse;
import com.gdsc.solutionchallenge.mission.exception.AlreadyCompletedMissionException;
import com.gdsc.solutionchallenge.mission.exception.AlreadyExistSpeciesMissionException;
import com.gdsc.solutionchallenge.mission.exception.MissionFailException;
import com.gdsc.solutionchallenge.mission.exception.MissionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoCreatureException.class)
    public ErrorResponse NoCreatureExceptionHandler(NoCreatureException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GeminiException.class)
    public ErrorResponse GeminiExceptionHandler(GeminiException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ImageNotFoundException.class)
    public ErrorResponse ImageNotFoundExceptionHandler(ImageNotFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoLatLngException.class)
    public ErrorResponse NoLatLngExceptionHandler(NoLatLngException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public ErrorResponse UnAuthorizedExceptionHandler(UnAuthorizedException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSpeciesException.class)
    public ErrorResponse NoSpeciesExceptionHandler(NoSpeciesException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistSpeciesMissionException.class)
    public ErrorResponse AlreadyExistSpeciesMissionExceptionHandler(AlreadyExistSpeciesMissionException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MissionNotFoundException.class)
    public ErrorResponse MissionNotFoundExceptionHandler(MissionNotFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissionFailException.class)
    public ErrorResponse MissionFailExceptionHandler(MissionFailException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyCompletedMissionException.class)
    public ErrorResponse AlreadyCompletedMissionExceptionHandler(AlreadyCompletedMissionException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .build();
        return response;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse ExceptionHandler(Exception e) {
        return ErrorResponse.builder()
                .code(String.valueOf(500))
                .message("server error! 카톡으로 연락주세용")
                .build();
    }
}
