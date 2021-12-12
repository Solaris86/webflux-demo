package com.vinsguru.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FailedInputValidationResponse {

    private int errorCode;
    private int input;
    private String message;

}
