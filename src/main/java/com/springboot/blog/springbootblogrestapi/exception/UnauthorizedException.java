package com.springboot.blog.springbootblogrestapi.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public UnauthorizedException(HttpStatus status,String message){
        super(message);
        this.status=status;
        this.message=message;
    }

    public UnauthorizedException(HttpStatus status,String message,String message2){
        super(message);
        this.status=status;
        this.message=message2;
    }
}
