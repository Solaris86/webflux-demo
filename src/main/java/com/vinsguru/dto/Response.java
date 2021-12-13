package com.vinsguru.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Response {

    private Date date;
    private int output;

    public Response(int output) {
        date = new Date();
        this.output = output;
    }
}
