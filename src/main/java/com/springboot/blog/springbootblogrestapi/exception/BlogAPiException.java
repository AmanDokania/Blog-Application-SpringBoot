package com.springboot.blog.springbootblogrestapi.exception;

import org.springframework.http.HttpStatus;

public class BlogAPiException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;

    public BlogAPiException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public BlogAPiException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
