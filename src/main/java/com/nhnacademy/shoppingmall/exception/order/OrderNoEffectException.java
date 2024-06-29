package com.nhnacademy.shoppingmall.exception.order;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class OrderNoEffectException extends WebApplicationException {
    public OrderNoEffectException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
