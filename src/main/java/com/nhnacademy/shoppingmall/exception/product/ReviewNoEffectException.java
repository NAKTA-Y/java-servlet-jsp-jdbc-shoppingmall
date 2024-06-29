package com.nhnacademy.shoppingmall.exception.product;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class ReviewNoEffectException extends WebApplicationException {
    public ReviewNoEffectException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
