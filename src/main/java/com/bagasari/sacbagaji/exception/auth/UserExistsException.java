package com.bagasari.sacbagaji.exception.auth;

public class UserExistsException extends RuntimeException{
    public UserExistsException(String message) {super(message);}
}
