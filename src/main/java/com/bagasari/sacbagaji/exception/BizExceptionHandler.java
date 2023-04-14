package com.bagasari.sacbagaji.exception;

import com.bagasari.sacbagaji.exception.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class BizExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleBizException(CustomException e, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), req.getRequestURI());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }
}
