package com.bagasari.sacbagaji.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // User
    USER_EXISTS(400, "A001", "이미 존재하는 유저입니다."),

    // JWT
    EMPTY_JWT_TOKEN(400, "B001", "JWT 토큰이 없습니다."),
    INVALID_TOKEN(400, "B002", "유효하지 않은 토큰입니다.");

    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
