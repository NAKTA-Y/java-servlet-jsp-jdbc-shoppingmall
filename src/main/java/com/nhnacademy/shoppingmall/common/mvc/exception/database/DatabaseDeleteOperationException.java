package com.nhnacademy.shoppingmall.common.mvc.exception.database;

import com.nhnacademy.shoppingmall.common.mvc.exception.WebApplicationException;

public class DatabaseDeleteOperationException extends WebApplicationException {
    private static final int STATUS_CODE = 500;
    private static final String DEFAULT_MESSGE = "데이터 베이스 삭제 작업 중 오류가 발생 했습니다.";

    public DatabaseDeleteOperationException() {
        this(STATUS_CODE, DEFAULT_MESSGE);
    }

    public DatabaseDeleteOperationException(int statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}
