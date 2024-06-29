package com.nhnacademy.shoppingmall.exception.user;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class AddressNotFoundException extends WebApplicationException {
    public AddressNotFoundException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
