package com.nhnacademy.shoppingmall.exception.product;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class CategoryNotFoundException extends WebApplicationException {
    public CategoryNotFoundException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
