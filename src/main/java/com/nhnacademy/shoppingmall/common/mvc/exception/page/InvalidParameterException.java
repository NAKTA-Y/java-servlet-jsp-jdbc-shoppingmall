package com.nhnacademy.shoppingmall.common.mvc.exception.page;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

import javax.servlet.http.HttpServletResponse;

public class InvalidParameterException extends WebApplicationException {
    private static final String NUMBER_FORMAT_EXCEPTION_MESSAGE = "파라미터 값이 숫자 형식이 아닙니다.";
    private static final String NULL_VALUE_EXCEPTION_MESSAGE = "필수 파라미터 값이 없습니다.";

    public InvalidParameterException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }

    public static InvalidParameterException numberFormatException() {
        return new InvalidParameterException(HttpServletResponse.SC_BAD_REQUEST, NUMBER_FORMAT_EXCEPTION_MESSAGE);
    }

    public static InvalidParameterException nullValueException() {
        return new InvalidParameterException(HttpServletResponse.SC_BAD_REQUEST, NULL_VALUE_EXCEPTION_MESSAGE);
    }
}
