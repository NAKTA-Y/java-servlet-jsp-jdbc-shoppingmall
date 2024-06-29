package com.nhnacademy.shoppingmall.exception.cart;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

import javax.servlet.http.HttpServletResponse;

public class CartOutOfStockException extends WebApplicationException {
    private static final int DEFAULT_STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
    private static final String DEFAULT_MESSAGE = "재고 수량을 초과하는 상품을 담을 수 없습니다.";

    public CartOutOfStockException() {
        this(DEFAULT_STATUS_CODE, DEFAULT_MESSAGE);
    }

    public CartOutOfStockException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
