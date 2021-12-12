package com.vinsguru.exception.handler;

import com.vinsguru.dto.FailedInputValidationResponse;
import com.vinsguru.exception.InputValidationException;
import com.vinsguru.exception.InvalidOperatorValueException;
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

    @ExceptionHandler(InvalidOperatorValueException.class)
    public ResponseEntity<String> handleInvalidOperatorValueException(InvalidOperatorValueException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
