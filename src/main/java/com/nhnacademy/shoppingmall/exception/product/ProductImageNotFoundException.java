package com.nhnacademy.shoppingmall.exception.product;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class ProductImageNotFoundException extends WebApplicationException {
    public ProductImageNotFoundException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
