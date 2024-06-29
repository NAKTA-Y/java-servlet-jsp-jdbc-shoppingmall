package com.nhnacademy.shoppingmall.exception.product;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class CategoryNoEffectException extends WebApplicationException {
    public CategoryNoEffectException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
