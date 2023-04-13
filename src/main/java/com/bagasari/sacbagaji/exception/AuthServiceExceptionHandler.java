package com.bagasari.sacbagaji.exception;

import com.bagasari.sacbagaji.exception.auth.UserExistsException;
import com.bagasari.sacbagaji.exception.security.EmptyJwtTokenException;
import com.bagasari.sacbagaji.exception.security.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class AuthServiceExceptionHandler {

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorResponse handleInvalidTokenException(UserExistsException exception, HttpServletRequest req) {
        return new ErrorResponse(LocalDateTime.now(), "BAD_REQUEST", exception.getMessage(), req.getRequestURI());
    }
}