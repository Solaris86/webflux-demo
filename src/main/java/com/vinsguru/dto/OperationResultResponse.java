package com.vinsguru.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationResultResponse {

    private final String operator;
    private final String result;



}
