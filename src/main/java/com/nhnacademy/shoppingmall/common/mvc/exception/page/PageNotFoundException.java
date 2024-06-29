package com.nhnacademy.shoppingmall.common.mvc.exception.page;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

import javax.servlet.http.HttpServletResponse;

public class PageNotFoundException extends WebApplicationException {
    private static final int DEFAULT_STATUS_CODE = HttpServletResponse.SC_NOT_FOUND;
    private static final String DEFAULT_MESSAGE = "페이지를 찾지 못했습니다.";
    public PageNotFoundException() {
        this(DEFAULT_STATUS_CODE, DEFAULT_MESSAGE);
    }

    public PageNotFoundException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
