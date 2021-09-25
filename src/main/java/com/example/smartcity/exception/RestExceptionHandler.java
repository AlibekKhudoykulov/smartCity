package com.example.smartcity.exception;

import com.example.smartcity.payload.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {RestException.class})
    public HttpEntity<ApiResponse> handleException(RestException ex) {
        return ResponseEntity.status(ex.getStatus()).body(
                new ApiResponse(
                        ex.getMessage(),
                        false
                )
        );
    }

    @ExceptionHandler(value = {Exception.class})
    public HttpEntity<ApiResponse> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                        "Server error",
                        false
                )
        );
    }
}
