package com.nhnacademy.shoppingmall.exception.user;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class AddressNoEffectException extends WebApplicationException {
    public AddressNoEffectException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
