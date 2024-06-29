package com.nhnacademy.shoppingmall.common.mvc.exception;

public class ServletInitilizeException extends RuntimeException{
    public ServletInitilizeException(String message) {
        super(String.format("웹 어플리케이션 초기화 작업 예외 발생: %s", message));
    }
}
