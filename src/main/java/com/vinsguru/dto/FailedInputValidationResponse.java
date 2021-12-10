package com.vinsguru.dto;

import lombok.Data;

@Data
public class FailedInputValidationResponse {

    private int errorCode;
    private int input;
    private String message;

}
