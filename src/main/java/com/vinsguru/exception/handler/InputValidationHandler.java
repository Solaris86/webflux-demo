package com.vinsguru.exception.handler;

import com.vinsguru.dto.FailedInputValidationResponse;
import com.vinsguru.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<FailedInputValidationResponse> handleException(InputValidationException ex) {
        FailedInputValidationResponse response = new FailedInputValidationResponse();
        response.setErrorCode(ex.getErrorCode());
        response.setInput(ex.getInput());
        response.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

}
