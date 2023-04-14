package com.bagasari.sacbagaji.exception.security;

import com.bagasari.sacbagaji.exception.ErrorCode;

public class InvalidTokenException extends RuntimeException{

    private ErrorCode errorCode = ErrorCode.INVALID_TOKEN;

    public InvalidTokenException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
