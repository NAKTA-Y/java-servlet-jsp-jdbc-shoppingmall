package com.nhnacademy.shoppingmall.exception.cart;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class CartNotFoundException extends WebApplicationException {
    public CartNotFoundException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
