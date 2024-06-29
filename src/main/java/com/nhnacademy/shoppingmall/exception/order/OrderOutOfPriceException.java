package com.nhnacademy.shoppingmall.exception.order;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

import javax.servlet.http.HttpServletResponse;

public class OrderOutOfPriceException extends WebApplicationException {
    private static final int DEFAULT_STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
    private static final String DEFAULT_MESSAGE = "포인트가 부족하여 주문이 실패 했습니다.";

    public OrderOutOfPriceException() {
        this (DEFAULT_STATUS_CODE, DEFAULT_MESSAGE);
    }

    public OrderOutOfPriceException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
