package com.gdsc.solutionchallenge.exception.advice;

import com.gdsc.solutionchallenge.ai.exception.NoCreatureException;
import com.gdsc.solutionchallenge.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

}
