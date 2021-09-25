package com.example.smartcity.exception;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {
    private String message;
    private HttpStatus status;

    public RestException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
