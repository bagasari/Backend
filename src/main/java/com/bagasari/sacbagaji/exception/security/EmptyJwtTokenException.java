package com.bagasari.sacbagaji.exception.security;

import com.bagasari.sacbagaji.exception.ErrorCode;

public class EmptyJwtTokenException extends RuntimeException{

    private ErrorCode errorCode = ErrorCode.EMPTY_JWT_TOKEN;

    public EmptyJwtTokenException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
