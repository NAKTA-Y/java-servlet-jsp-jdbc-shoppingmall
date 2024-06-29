package com.nhnacademy.shoppingmall.exception.product;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class ProductCategoryNotFoundException extends WebApplicationException {
    public ProductCategoryNotFoundException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
