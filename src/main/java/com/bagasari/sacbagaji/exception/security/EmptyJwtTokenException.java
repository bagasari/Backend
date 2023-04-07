package com.bagasari.sacbagaji.exception.security;

public class EmptyJwtTokenException extends RuntimeException{
    public EmptyJwtTokenException(String message) {
        super(message);
    }
}
