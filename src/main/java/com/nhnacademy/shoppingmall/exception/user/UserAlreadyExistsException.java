package com.nhnacademy.shoppingmall.exception.user;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class UserAlreadyExistsException extends WebApplicationException {
    public UserAlreadyExistsException(int statusCode, String message){
        super(statusCode, message);
    }
}
