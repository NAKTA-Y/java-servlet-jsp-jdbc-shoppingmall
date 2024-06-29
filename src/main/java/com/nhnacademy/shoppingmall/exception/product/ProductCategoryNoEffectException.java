package com.nhnacademy.shoppingmall.exception.product;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class ProductCategoryNoEffectException extends WebApplicationException {
    public ProductCategoryNoEffectException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
