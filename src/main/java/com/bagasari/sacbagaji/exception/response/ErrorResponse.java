package com.bagasari.sacbagaji.exception.response;

import com.bagasari.sacbagaji.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss",
        timezone = "Asia/Seoul"
    )
    private LocalDateTime localDateTime;
    private int status;
    private String code; // custom code
    private String message;
    private String requestURI;

    public ErrorResponse(ErrorCode errorCode, String requestURI) {
        this.localDateTime = LocalDateTime.now();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.requestURI = requestURI;
    }

    public ErrorResponse(ErrorCode errorCode, String message, String requestURI) {
        this.localDateTime = LocalDateTime.now();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = message;
        this.requestURI = requestURI;
    }
}
