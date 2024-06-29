package com.nhnacademy.shoppingmall.exception.order;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class OrderNotFoundException extends WebApplicationException {
    public OrderNotFoundException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
