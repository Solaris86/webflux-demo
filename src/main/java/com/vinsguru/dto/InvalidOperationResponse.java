package com.vinsguru.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvalidOperationResponse {

    private final String message;

}
