package com.nhnacademy.shoppingmall.exception.user;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class UserNotFoundException extends WebApplicationException {
    public UserNotFoundException(int statusCode, String message){
        super(statusCode, message);
    }
}
