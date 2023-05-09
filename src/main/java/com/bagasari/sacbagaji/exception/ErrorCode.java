package com.bagasari.sacbagaji.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // LOGIN
    USER_EXISTS(400, "A001", "이미 존재하는 유저입니다."),
    BAD_CREDENTIAL_NONEXISTENT_ID(400, "A002", "자격증명 실패 - 존재하지 않는 이메일"),
    BAD_CREDENTIAL_INVALID_PWD(400, "A003", "자격증명 실패 - 잘못된 비밀번호"),
    // JWT
    EMPTY_JWT_TOKEN(400, "B001", "JWT 토큰이 없습니다."),
    INVALID_TOKEN(400, "B002", "유효하지 않은 토큰입니다."),
    CANT_REFRESH_TOKEN(400, "B003", "JWT 토큰을 재발급 받을 수 없습니다."),
    // 가계부
    NONEXISTENT_ACCOUNT_ID(400, "C001", "존재하지 않는 가계부 입니다."),
    INVALID_ACCESS_ACCOUNT(400, "C002", "유저가 생성한 가계부가 아닙니다."),
    // 품목
    INVALID_PRODUCT_TYPE(400, "d001", "존재하지 않는 품목 타입입니다.");

    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
