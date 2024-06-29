package com.nhnacademy.shoppingmall.exception.product;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class ProductImageNoEffectException extends WebApplicationException {
    public ProductImageNoEffectException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
