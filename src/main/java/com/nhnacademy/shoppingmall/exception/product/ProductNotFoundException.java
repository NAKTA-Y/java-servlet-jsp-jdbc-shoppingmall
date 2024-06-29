package com.nhnacademy.shoppingmall.exception.product;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class ProductNotFoundException extends WebApplicationException {
    public ProductNotFoundException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
