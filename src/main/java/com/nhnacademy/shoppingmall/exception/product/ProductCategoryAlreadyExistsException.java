package com.nhnacademy.shoppingmall.exception.product;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class ProductCategoryAlreadyExistsException extends WebApplicationException {
    public ProductCategoryAlreadyExistsException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
