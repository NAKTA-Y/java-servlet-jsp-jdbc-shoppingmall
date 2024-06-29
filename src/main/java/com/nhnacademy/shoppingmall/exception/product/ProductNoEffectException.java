package com.nhnacademy.shoppingmall.exception.product;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class ProductNoEffectException extends WebApplicationException {
    public ProductNoEffectException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
