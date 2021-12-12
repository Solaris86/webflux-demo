package com.vinsguru.exception;

public class InvalidOperatorValueException extends RuntimeException {

    private final String value;

    public InvalidOperatorValueException(String message, String value) {
        super(message);
        this.value = value;
    }
}
