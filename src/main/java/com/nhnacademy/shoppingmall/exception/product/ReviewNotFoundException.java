package com.nhnacademy.shoppingmall.exception.product;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class ReviewNotFoundException extends WebApplicationException {
    public ReviewNotFoundException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
