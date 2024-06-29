package com.nhnacademy.shoppingmall.common.mvc.exception.page;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

import javax.servlet.http.HttpServletResponse;

public class UnAuthorizedException extends WebApplicationException {
    private static final int DEFAULT_STATUS_CODE = HttpServletResponse.SC_UNAUTHORIZED;
    private static final String DEFAULT_MESSAGE = "해당 요청 권한이 없습니다.";

    public UnAuthorizedException() {
        this (DEFAULT_STATUS_CODE, DEFAULT_MESSAGE);
    }

    public UnAuthorizedException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
