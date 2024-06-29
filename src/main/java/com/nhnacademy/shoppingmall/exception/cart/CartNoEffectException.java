package com.nhnacademy.shoppingmall.exception.cart;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class CartNoEffectException extends WebApplicationException {
    public CartNoEffectException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
