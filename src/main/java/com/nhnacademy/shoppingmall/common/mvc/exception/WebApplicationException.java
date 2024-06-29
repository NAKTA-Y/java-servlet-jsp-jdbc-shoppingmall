package com.nhnacademy.shoppingmall.common.mvc.exception;

import lombok.Getter;

@Getter
public class WebApplicationException extends RuntimeException{
    private final int statusCode;
    private final String errorMessage;

    public WebApplicationException(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }
}
